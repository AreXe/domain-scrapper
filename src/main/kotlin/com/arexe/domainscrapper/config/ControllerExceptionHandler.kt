package com.arexe.domainscrapper.config

import com.arexe.domainscrapper.utils.LoggerTrait
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler : LoggerTrait {

    @ExceptionHandler(Exception::class)
    fun handle(e: Exception) {
        logger.error("Unexpected exception occurred", e)
        throw e
    }
}