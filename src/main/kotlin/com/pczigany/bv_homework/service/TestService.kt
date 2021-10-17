package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.free_nba_api.FreeNbaGame
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaStat
import com.pczigany.bv_homework.repository.GameRepository
import com.pczigany.bv_homework.repository.LookedUpDatesRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TestService(
    private val gameRepository: GameRepository,
    private val lookedUpDatesRepository: LookedUpDatesRepository,
    private val freeNbaClientService: FreeNbaClientService
) {


    fun getTestStatsForGame(gameId: String): List<FreeNbaStat>? =
        freeNbaClientService.getStatsForGame(gameId)

    fun getTestGame(gameId: String): FreeNbaGame? =
        freeNbaClientService.getGame(gameId)

    fun getTestGamesForDate(date: String): List<FreeNbaGame>? =
        freeNbaClientService.getGamesForDate(LocalDate.parse(date))

}