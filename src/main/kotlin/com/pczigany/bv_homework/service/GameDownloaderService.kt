package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.document.Game
import com.pczigany.bv_homework.data.document.PlayerScore
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaGame
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaStat
import com.pczigany.bv_homework.repository.GameRepository
import com.pczigany.bv_homework.util.ConverterUtil.toDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.Date

@Service
class GameDownloaderService(
    private val gameRepository: GameRepository,
    private val freeNbaClientService: FreeNbaClientService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun getAndStoreAllGamesForDate(date: Date): List<String> {
        val freeNbaGames = freeNbaClientService.getGamesForDate(date)
        freeNbaGames.forEach { freeNbaGame ->
            val playerScores = countPlayerScores(freeNbaClientService.getStatsForGame(freeNbaGame.id.toString()))

            saveGameFromNbaGameAndPlayerScores(freeNbaGame, playerScores)
        }
        return freeNbaGames.map { it.id.toString() }
    }

    fun getAndStoreGame(gameId: String) {
        val game = freeNbaClientService.getGame(gameId) ?: return
        val playerScores = countPlayerScores(freeNbaClientService.getStatsForGame(gameId))
        saveGameFromNbaGameAndPlayerScores(game, playerScores)
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
        val game = gameRepository.save(
            Game(
                gameId = freeNbaGame.id.toString(),
                date = freeNbaGame.date?.subSequence(0, 10)?.toDate(),
                homeTeamName = freeNbaGame.homeTeam?.fullName,
                homeTeamScore = freeNbaGame.homeTeamScore,
                visitingTeamName = freeNbaGame.visitorTeam?.fullName,
                visitingTeamScore = freeNbaGame.visitorTeamScore,
                playerScores = playerScores
            )
        )
        logger.info("Game $game saved successfully.")
    }
}
