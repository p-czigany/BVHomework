package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.input.CommentRequest
import com.pczigany.bv_homework.exception.CommentNotFoundException
import com.pczigany.bv_homework.exception.GameNotFoundException
import com.pczigany.bv_homework.repository.GameRepository
import com.pczigany.bv_homework.util.ConverterUtil.asDocument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val gameRepository: GameRepository,
    private val gameService: GameService
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun addComment(gameId: String, commentRequest: CommentRequest) {
        gameService.ensurePersistenceForId(gameId)
        val game = gameRepository.findByIdOrNull(gameId) ?: throw GameNotFoundException()
        game.comments.add(commentRequest.asDocument())
        game.comments.sortByDescending { it.timestamp }
        gameRepository.save(game)
        logger.info("Comment added to game $gameId with the message of ${commentRequest.message}.")
    }

    fun updateComment(gameId: String, commentId: String, commentRequest: CommentRequest) {
        val game = gameRepository.findByIdOrNull(gameId) ?: throw GameNotFoundException()
        game.comments.remove(game.comments.find { it.id == commentId }
            ?: throw CommentNotFoundException())
        game.comments.add(commentRequest.asDocument())
        game.comments.sortByDescending { it.timestamp }
        gameRepository.save(game)
        logger.info("Comment $commentId updated.")
    }

    fun deleteComment(gameId: String, commentId: String) {
        val game = gameRepository.findByIdOrNull(gameId) ?: throw GameNotFoundException()
        game.comments.remove(game.comments.find { it.id == commentId }
            ?: throw CommentNotFoundException())
        gameRepository.save(game)
        logger.info("Comment $commentId deleted.")
    }
}
