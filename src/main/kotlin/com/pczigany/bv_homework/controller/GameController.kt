package com.pczigany.bv_homework.controller

import com.pczigany.bv_homework.data.document.Game
import com.pczigany.bv_homework.service.GameService
import com.pczigany.bv_homework.util.ConverterUtil.toDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.text.ParseException

@RestController
@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
    value = [""]
)
class GameController(
    private val gameService: GameService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/games")
    fun getGamesForDate(
        @RequestParam date: String
    ): ResponseEntity<List<Game>> {
        logger.info("Received get games request for date $date")
        val validDate = try {
            date.toDate()
        } catch (parseException: ParseException) {
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok(gameService.getByDate(validDate))
    }

    @GetMapping("/games/{gameId}")
    fun getGameById(
        @PathVariable gameId: String
    ): ResponseEntity<Game> {
        logger.info("Received get game request for game $gameId")

        val game = gameService.getById(gameId)
        return ResponseEntity.ok(game)
    }
}
