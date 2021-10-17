package com.pczigany.bv_homework.controller

import com.pczigany.bv_homework.data.free_nba_api.FreeNbaGame
import com.pczigany.bv_homework.data.free_nba_api.FreeNbaStat
import com.pczigany.bv_homework.service.TestService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
    value = ["/test"]
)
class TestController(
    private val testService: TestService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/games/{gameId}/stats") // TODO: Delete this one
    fun getTestStatsForGame(
        @PathVariable gameId: String
    ): ResponseEntity<List<FreeNbaStat>> {
        logger.info("Received request on the STATS TEST ENDPOINT")
        return ResponseEntity.ok(testService.getTestStatsForGame(gameId))
    }

    @GetMapping("/games/{gameId}") // TODO: Delete this one
    fun getTestGame(
        @PathVariable gameId: String
    ): ResponseEntity<FreeNbaGame> {
        logger.info("Received request on the GAME TEST ENDPOINT")
        return ResponseEntity.ok(testService.getTestGame(gameId))
    }

    @GetMapping("/games") // TODO: Delete this one
    fun getTestGamesForDate(
        @RequestParam date: String
    ): ResponseEntity<List<FreeNbaGame>> {
        logger.info("Received request on the GAME TEST ENDPOINT")
        return ResponseEntity.ok(testService.getTestGamesForDate(date))
    }
}
