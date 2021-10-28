package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.document.Game
import com.pczigany.bv_homework.repository.GameRepository
import io.kotlintest.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.data.repository.findByIdOrNull
import java.util.Date

internal class GameServiceTest {

    private val gameRepository = mockk<GameRepository>()
    private val cacheService = mockk<CacheService>()
    private val subject = GameService(gameRepository, cacheService)

    @Test
    fun getByDate() {
        val date = mockk<Date>()
        val gamesForDate = mockk<List<Game>>()
        every { cacheService.ensurePersistenceForDate(date) } just Runs
        every { gameRepository.findByDate(date) } returns gamesForDate

        val result = subject.getByDate(date)

        result shouldBe gamesForDate
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetById {
        @BeforeAll
        internal fun beforeAll() {
            every { cacheService.ensurePersistenceForGameId(any()) } just Runs
        }

        @Test
        fun `successfully gets game by ID`() {
            val gameId = "gameId"
            val gameForId = mockk<Game>()
            every { gameRepository.findByIdOrNull(gameId) } returns gameForId

            val result = subject.getById(gameId)

            result shouldBe gameForId
        }

        @Test
        fun `fails to get game by ID -- not such game`() {
            val gameId = "gameId"
            every { gameRepository.findByIdOrNull(gameId) } returns null

            val result = subject.getById(gameId)

            result shouldBe null
        }
    }
}
