package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.document.Game
import com.pczigany.bv_homework.data.document.LookedUpDate
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaGame
import com.pczigany.bv_homework.repository.GameRepository
import com.pczigany.bv_homework.repository.LookedUpDatesRepository
import com.pczigany.bv_homework.repository.LookedUpGamesRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.Date

internal class GameServiceTest {

    private val lookedUpGamesRepository = mockk<LookedUpGamesRepository>()
    private val lookedUpDatesRepository = mockk<LookedUpDatesRepository>()
    private val gameRepository = mockk<GameRepository>()
    private val freeNbaClientService = mockk<FreeNbaClientService>()
    private val subject = GameService(
        lookedUpGamesRepository,
        lookedUpDatesRepository,
        gameRepository,
        freeNbaClientService
    )

    @Nested
    inner class GetByDate {
        @Test
        fun `successfully get games for date when date has been already looked up`() {
            val date = mockk<Date>()
            val games = mockk<List<Game>>()
            every { lookedUpDatesRepository.existsById(any()) } returns true
            every { gameRepository.findByDate(date) } returns games

            val result = subject.getByDate(date)

            result shouldBe games
        }

        @Test
        fun `successfully get games for date when date has yet to be looked up`() {
            val date = mockk<Date>()
            val freeNbaGameIds = mockk<List<Int?>>()
            val lookedUpDate = LookedUpDate(date)

            every { lookedUpDatesRepository.existsById(any()) } returns false
            every { lookedUpDatesRepository.save(lookedUpDate)} returns lookedUpDate


        }

        @Test
        fun `fail to get games for date`() {

        }
    }

    @Nested
    inner class GetById {
        @Test
        fun `successfully get game by id`() {
        }

        @Test
        fun `fail to get game by id - game does not exist`() {

        }
    }
}
