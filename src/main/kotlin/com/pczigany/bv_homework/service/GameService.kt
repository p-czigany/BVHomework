package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.document.LookedUpDate
import com.pczigany.bv_homework.data.document.LookedUpGame
import com.pczigany.bv_homework.data.document.PlayerScore
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaGame
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaStat
import com.pczigany.bv_homework.data.input.CommentRequest
import com.pczigany.bv_homework.repository.GameRepository
import com.pczigany.bv_homework.repository.LookedUpDatesRepository
import com.pczigany.bv_homework.repository.LookedUpGamesRepository
import com.pczigany.bv_homework.util.ConverterUtil.asDocument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import com.pczigany.bv_homework.data.document.Game as GameDocument

@Service
class GameService(
    private val lookedUpGamesRepository: LookedUpGamesRepository,
    private val lookedUpDatesRepository: LookedUpDatesRepository,
    private val gameRepository: GameRepository,
    private val freeNbaClientService: FreeNbaClientService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun getByDate(date: LocalDate): List<GameDocument> {
        ensurePersistenceForDate(date)
        return gameRepository.findByDate(date)
    }

    private fun ensurePersistenceForDate(date: LocalDate) {
        if (!lookedUpDatesRepository.existsById(date)) {
            getAndStoreAllGamesForDate(date)
            lookedUpDatesRepository.save(LookedUpDate(date))
        }
    }

    private fun getAndStoreAllGamesForDate(date: LocalDate) {
        val gameIds = freeNbaClientService.getGamesForDate(date).map { it.id }
        gameIds.forEach { gameId -> ensurePersistenceForId(gameId.toString()) }
    }

    fun ensurePersistenceForId(gameId: String) {
        when (lookedUpGamesRepository.existsById(gameId)) {
            true -> logger.info("We have already tried to cache game $gameId.")
            false -> {
                logger.info("We still have to try caching game $gameId.")
                getAndPersistGame(gameId)
                lookedUpGamesRepository.save(LookedUpGame(gameId))
            }
        }
    }

    fun getById(gameId: String): GameDocument? {
        ensurePersistenceForId(gameId)
        return gameRepository.findByIdOrNull(gameId)
    }

    private fun getAndPersistGame(gameId: String) {

        val game = freeNbaClientService.getGame(gameId) ?: return

        val playerScores = countPlayerScores(freeNbaClientService.getStatsForGame(gameId))

        saveGameFromNbaGameAndPlayerScores(
            game,
            playerScores
        )
    }

    private fun countPlayerScores(stats: List<FreeNbaStat>) =
        stats.groupingBy(FreeNbaStat::player)
            .fold(PlayerScore("", "", 0)) { acc, elem ->
                PlayerScore(
                    elem.player.id,
                    elem.player.firstName + " " + elem.player.lastName,
                    acc.score + (elem.pts ?: 0)
                )
            }
            .values.filterNot { it.score == 0 }

    private fun saveGameFromNbaGameAndPlayerScores(
        freeNbaGame: FreeNbaGame,
        playerScores: List<PlayerScore>
    ) {
        logger.info("I want to save the composed game.")
        gameRepository.save(
            GameDocument(
                gameId = freeNbaGame.id.toString(),
                date = LocalDate.parse(freeNbaGame.date?.subSequence(0, 10)),
                homeTeamName = freeNbaGame.homeTeam?.fullName,
                homeTeamScore = freeNbaGame.homeTeamScore,
                visitingTeamName = freeNbaGame.visitorTeam?.fullName,
                visitingTeamScore = freeNbaGame.visitorTeamScore,
                playerScores = playerScores
            )
        )
        logger.info("Game Successfully saved.")
    }
}
