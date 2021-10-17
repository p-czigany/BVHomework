package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.document.PersistedDate
import com.pczigany.bv_homework.data.document.PlayerScore
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaGame
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaStat
import com.pczigany.bv_homework.data.input.CommentRequest
import com.pczigany.bv_homework.repository.GameRepository
import com.pczigany.bv_homework.repository.PersistedDatesRepository
import com.pczigany.bv_homework.util.ConverterUtil.asDocument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import com.pczigany.bv_homework.data.document.Game as GameDocument

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val persistedDatesRepository: PersistedDatesRepository,
    private val freeNbaClientService: FreeNbaClientService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun getByDate(date: LocalDate): List<GameDocument> {
        ensurePersistanceForDate(date)
        return gameRepository.findByDate(date)
    }

    private fun ensurePersistanceForDate(date: LocalDate) {
        if (!persistedDatesRepository.existsById(date)) {
            getAndStoreAllGamesForDate(date)
            persistedDatesRepository.save(PersistedDate(date))
        }
    }

    private fun getAndStoreAllGamesForDate(date: LocalDate) {
        val gameIds = freeNbaClientService.getGamesForDate(date).map { it.id }
        gameIds.forEach { gameId -> ensurePersistanceForId(gameId.toString()) }
    }

    private fun ensurePersistanceForId(gameId: String) {
        if (!gameRepository.existsById(gameId)) {
            getAndPersistGame(gameId)
        }
    }

    fun getById(gameId: String): GameDocument? {
        ensurePersistanceForId(gameId)
        return gameRepository.findByIdOrNull(gameId)
    }

    private fun getAndPersistGame(gameId: String) =
        saveGameFromNbaGameAndPlayerScores(
            freeNbaClientService.getGame(gameId),
            countPlayerScores(freeNbaClientService.getStatsForGame(gameId))
        )

    private fun countPlayerScores(stats: List<FreeNbaStat>) =
        stats.groupingBy(FreeNbaStat::player)
            .fold(PlayerScore(0, "", 0)) { acc, elem ->
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
    }

    fun addComment(gameId: String, commentRequest: CommentRequest) {
        ensurePersistanceForId(gameId)
        val game = gameRepository.findByIdOrNull(gameId)
        if (game != null) {
            game.comments.add(commentRequest.asDocument())
            game.comments.sortBy { it.timestamp }
            gameRepository.save(game)
        } // TODO: Exception with not finding the resource
    }

    fun updateComment(gameId: String, commentId: String, commentRequest: CommentRequest) {
        val game = gameRepository.findByIdOrNull(gameId)
        if (game != null) {
            game.comments.remove(game.comments.find { it.id == commentId })
            game.comments.add(commentRequest.asDocument())
            game.comments.sortBy { it.timestamp }
            gameRepository.save(game)
        } // TODO: Exception with not finding the resource
    }

    fun deleteComment(gameId: String, commentId: String) {
        val game = gameRepository.findByIdOrNull(gameId)
        game?.comments?.remove(game.comments.find { it.id == commentId }) // TODO: Exception with not finding the resource
    }
}
