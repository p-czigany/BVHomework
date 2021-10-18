package com.pczigany.bv_homework.controller

import com.pczigany.bv_homework.data.input.CommentRequest
import com.pczigany.bv_homework.exception.GameNotFoundException
import com.pczigany.bv_homework.service.CommentService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK

class CommentsControllerTest {

    private val commentService = mockk<CommentService>()
    private val subject = CommentController(commentService)

    @Nested
    inner class AddComment {

        @Test
        fun `add comment calls CommentService`() {
            every { commentService.addComment(any(), any()) } returns Unit

            subject.addComment("gameId", CommentRequest("someComments"))

            verify { commentService.addComment(any(), any()) }
        }
    }

    @Nested
    inner class UpdateComment {

        @Test
        fun `update comment calls CommentService`() {
            val commentRequest = mockk<CommentRequest>()
            every { commentService.updateComment(any(), any(), any()) } returns Unit

            subject.updateComment("gameId", "commentId", commentRequest)

            verify { commentService.updateComment(any(), any(), commentRequest) }
        }

        @Test
        fun `successfully update comment - respond with 200 OK`() {
            val commentRequest = mockk<CommentRequest>()
            every { commentService.updateComment(any(), any(), any()) } returns Unit

            subject.updateComment("gameId", "commentId", commentRequest)
        }
    }

    @Nested
    inner class DeleteComment {

        @Test
        fun `delete comment calls CommentService`() {
            every { commentService.deleteComment(any(), any()) } returns Unit

            subject.deleteComment("gameId", "commentId")

            verify { commentService.deleteComment(any(), any()) }
        }

        @Test
        fun `successfully delete comment - respond with 200 OK`() {
            every { commentService.deleteComment(any(), any()) } returns Unit

            val response = subject.deleteComment("gameId", "commentId")

            response.statusCode shouldBe OK
        }
    }
}
