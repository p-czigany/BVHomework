package com.pczigany.bv_homework.controller

import com.pczigany.bv_homework.data.document.Game
import com.pczigany.bv_homework.data.input.CommentRequest
import com.pczigany.bv_homework.service.GameService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
    value = [""]
)
class Controller(
    private val gameService: GameService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/games/{date}")
    fun getGamesForDate(
        @PathVariable date: String
    ): ResponseEntity<List<Game>> {
        logger.info("Received get games request for date $date")
        return ResponseEntity.ok(
            gameService.getByDate(
                LocalDate.parse(
                    date,
                    DateTimeFormatter.ISO_DATE
                )
            )
        )
    }

    @GetMapping("/games/{gameId}")
    fun getGame(
        @PathVariable gameId: String
    ): ResponseEntity<Game> {
        logger.info("Received get game request for game $gameId")
        return ResponseEntity.ok(gameService.getById(gameId))
    }

    @PostMapping("/games/{gameId}/comments")
    fun addComment(
        @PathVariable gameId: String,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Unit> {
        logger.info("Received add comment request for game $gameId")
        return ResponseEntity.ok(gameService.addComment(gameId, commentRequest))
    }

    @PutMapping("/games/{gameId}/comments/{commentId}")
    fun updateComment(
        @PathVariable gameId: String,
        @PathVariable commentId: String,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Unit> {
        logger.info("Received update comment request for comment $commentId of game $gameId")
        return ResponseEntity.ok(gameService.updateComment(gameId, commentId, commentRequest))
    }

    @DeleteMapping("/games/{gameId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable gameId: String,
        @PathVariable commentId: String
    ): ResponseEntity<Unit> {
        logger.info("Received delete comment request for comment $commentId of game $gameId")
        return ResponseEntity.ok(gameService.deleteComment(gameId, commentId))
    }
}
