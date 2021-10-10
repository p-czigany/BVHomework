package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.document.PersistedDate
import com.pczigany.bv_homework.data.document.PlayerScore
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaGame
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaStat
import com.pczigany.bv_homework.data.input.CommentRequest
import com.pczigany.bv_homework.repository.GameRepository
import com.pczigany.bv_homework.repository.PersistedDatesRepository
import com.pczigany.bv_homework.util.ConverterUtil.asDocument
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import com.pczigany.bv_homework.data.document.Game as GameDocument


@Service
class GameService(
    private val gameRepository: GameRepository,
    private val persistedDatesRepository: PersistedDatesRepository,
    private val freeNbaClientService: FreeNbaClientService
) {
    fun getByDate(date: LocalDate): List<GameDocument> {
        if (!areAllGamesForDatePersisted(date)) {
            getAndStoreGamesForDate(date)
        }
        return gameRepository.findByDate(date)
    }

    private fun areAllGamesForDatePersisted(date: LocalDate) = persistedDatesRepository.existsById(date)

    private fun getAndStoreGamesForDate(date: LocalDate) {
        getAndStoreAllGamesForDate(date)
        persistedDatesRepository.save(PersistedDate(date))
    }

    private fun getAndStoreAllGamesForDate(date: LocalDate) {
        freeNbaClientService.getGamesForDate(date)
    }

    fun getById(gameId: String): GameDocument? {
        if (!isGamePersisted(gameId)) {
            getAndPersistGame(gameId)
        }
        return gameRepository.findByIdOrNull(gameId)
    }

    private fun isGamePersisted(gameId: String) = gameRepository.existsById(gameId)

    private fun getAndPersistGame(gameId: String) =
        saveGameFromNbaGameAndPlayerScores(freeNbaClientService.getGame(gameId), countPlayerScores(freeNbaClientService.getStatsForGame(gameId)))

    private fun countPlayerScores(stats: List<FreeNbaStat>) =
        stats.groupingBy(FreeNbaStat::player)
            .fold(PlayerScore(0, "", 0)) { acc, elem ->
                PlayerScore(
                    elem.player.id,
                    elem.player.firstName + " " + elem.player.lastName,
                    acc.score + elem.pts
                )
            }
            .values.filterNot { it.score == 0 }

    private fun saveGameFromNbaGameAndPlayerScores(freeNbaGame: FreeNbaGame, playerScores: List<PlayerScore>) {
        gameRepository.save(
            GameDocument(
                gameId = freeNbaGame.id,
                date = LocalDate.parse(freeNbaGame.date.subSequence(0, 10)),
                homeTeamName = freeNbaGame.homeTeam.fullName,
                homeTeamScore = freeNbaGame.homeTeamScore,
                visitingTeamName = freeNbaGame.visitorTeam.fullName,
                visitingTeamScore = freeNbaGame.visitorTeamScore,
                playerScores = playerScores
            )
        )
    }

    fun addComment(gameId: String, commentRequest: CommentRequest) {
        if (!isGamePersisted(gameId)) {
            getAndPersistGame(gameId)
        }
        val game = gameRepository.findByIdOrNull(gameId)
        if (game != null) {
            game.comments.add(commentRequest.asDocument())
            game.comments.sortBy { it.timestamp }
            gameRepository.save(game)
        }
    }

    fun updateComment(gameId: String, commentId: String, commentRequest: CommentRequest) {
    }

    fun deleteComment(gameId: String, commentId: String) {
    }
}
