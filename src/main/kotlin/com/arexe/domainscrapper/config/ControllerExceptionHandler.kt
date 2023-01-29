package com.arexe.domainscrapper.config

import com.arexe.domainscrapper.utils.LoggerTrait
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler : LoggerTrait {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(e: BadRequestException): ResponseEntity<ErrorMessage> {
        logger.warn("Bad request exception occurred", e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorMessage(e.message))
    }

    @ExceptionHandler(Exception::class)
    fun handle(e: Exception) {
        logger.error("Unexpected exception occurred", e)
        throw e
    }
}

class BadRequestException(message: String) : Exception(message)

data class ErrorMessage(val message: String?)