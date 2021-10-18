package com.pczigany.bv_homework.controller

import com.pczigany.bv_homework.data.document.Game
import com.pczigany.bv_homework.service.GameService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity

internal class GameControllerTest {

    private val gameService = mockk<GameService>()
    private val subject = GameController(gameService)

    @Nested
    inner class GetGamesForDate {

        @Test
        fun `get games for date calls GameService`() {
            val games = mockk<List<Game>>()
            val validDateString = "1999-05-20"
            every { gameService.getByDate(any()) } returns games

            subject.getGamesForDate(validDateString)

            verify { gameService.getByDate(any()) }
        }

        @Test
        fun `successfully get games for valid date - respond with 200 OK`() {
            val games = mockk<List<Game>>()
            val validDateString = "1999-05-20"
            every { gameService.getByDate(any()) } returns games

            val result = subject.getGamesForDate(validDateString)

            result.statusCode shouldBe OK
            result.body shouldBe games
        }

        @Test
        fun `fail to get games for date because of invalid date input - respond with 400 Bad Request`() {
            val games = mockk<List<Game>>()
            val invalidDateString = "2222211-01"
            every { gameService.getByDate(any()) } returns games

            val response = subject.getGamesForDate("someInvalidDateString")

            response.statusCode shouldBe BAD_REQUEST
        }
    }

    @Nested
    inner class GetGameById {
        @Test
        fun `get game by id calls GameService`() {
            val game = mockk<Game>()
            every { gameService.getById(any()) } returns game

            subject.getGameById("someGameId")

            verify { gameService.getById("someGameId") }
        }

        @Test
        fun `successfully get game by Id - respond with 200 Ok`() {
            val game = mockk<Game>() //{ every { gameId } returns "gameId" }
            every { gameService.getById("gameId") } returns game

            val result = subject.getGameById("gameId")

            result.statusCode shouldBe OK
            result.body shouldBe game
        }
    }
}
