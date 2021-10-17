package com.pczigany.bv_homework.controller

import com.pczigany.bv_homework.data.input.CommentRequest
import com.pczigany.bv_homework.service.CommentService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
    value = [""]
)
class CommentController(
    private val commentService: CommentService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/games/{gameId}/comments")
    fun addComment(
        @PathVariable gameId: String,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Unit> {
        logger.info("Received add comment request for game $gameId")
        return ResponseEntity.ok(commentService.addComment(gameId, commentRequest))
    }

    @PutMapping("/games/{gameId}/comments/{commentId}")
    fun updateComment(
        @PathVariable gameId: String,
        @PathVariable commentId: String,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Unit> {
        logger.info("Received update comment request for comment $commentId of game $gameId")
        return ResponseEntity.ok(commentService.updateComment(gameId, commentId, commentRequest))
    }

    @DeleteMapping("/games/{gameId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable gameId: String,
        @PathVariable commentId: String
    ): ResponseEntity<Unit> {
        logger.info("Received delete comment request for comment $commentId of game $gameId")
        return ResponseEntity.ok(commentService.deleteComment(gameId, commentId))
    }
}
