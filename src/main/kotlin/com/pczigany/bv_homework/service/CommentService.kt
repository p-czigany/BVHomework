package com.pczigany.bv_homework.service

import com.pczigany.bv_homework.data.input.CommentRequest
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
        val game = gameRepository.findByIdOrNull(gameId)
        if (game != null) {
            game.comments.add(commentRequest.asDocument())
            game.comments.sortBy { it.timestamp }
            gameRepository.save(game)
        } // TODO: Exception with not finding the resource
    }

    fun updateComment(gameId: String, commentId: String, commentRequest: CommentRequest) {
        val game = gameRepository.findByIdOrNull(gameId)
        if (game != null) {
            game.comments.remove(game.comments.find { it.id == commentId })
            game.comments.add(commentRequest.asDocument())
            game.comments.sortBy { it.timestamp }
            gameRepository.save(game)
        } // TODO: Exception with not finding the resource
    }

    fun deleteComment(gameId: String, commentId: String) {
        val game = gameRepository.findByIdOrNull(gameId)
        game?.comments?.remove(game.comments.find { it.id == commentId }) // TODO: Exception with not finding the resource
    }
}