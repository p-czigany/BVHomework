package com.pczigany.bv_homework.controller

import com.pczigany.bv_homework.exception.CommentNotFoundException
import com.pczigany.bv_homework.exception.FreeNbaApiException
import com.pczigany.bv_homework.exception.GameNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [CommentNotFoundException::class])
    protected fun handleCommentNotFound(
        ex: RuntimeException?, request: WebRequest?
    ): ResponseEntity<Any> {
        val bodyOfResponse = "Comment Not Found"
        return handleExceptionInternal(
            ex!!, bodyOfResponse,
            HttpHeaders(), NOT_FOUND, request!!
        )
    }

    @ExceptionHandler(value = [GameNotFoundException::class])
    protected fun handleGameNotFound(
        ex: RuntimeException?, request: WebRequest?
    ): ResponseEntity<Any> {
        val bodyOfResponse = "Game Not Found"
        return handleExceptionInternal(
            ex!!, bodyOfResponse,
            HttpHeaders(), NOT_FOUND, request!!
        )
    }

    @ExceptionHandler(value = [FreeNbaApiException::class])
    protected fun handleApiCommunicationError(
        ex: RuntimeException?, request: WebRequest?
    ): ResponseEntity<Any> {
        val bodyOfResponse = "Error in communication with 3rd party API"
        return handleExceptionInternal(
            ex!!, bodyOfResponse,
            HttpHeaders(), NOT_FOUND, request!!
        )
    }
}
