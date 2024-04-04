package com.food.ordering.system.application.handler

import com.food.ordering.system.domain.util.logger
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    val log = logger()

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(exception: Exception): ErrorDTO {
        log.error(exception.message, exception)
        return ErrorDTO(
            code = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = exception.message ?: "Internal server error"
        )
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(validationException: ValidationException): ErrorDTO {

        when (validationException) {
            is ConstraintViolationException -> {
                extractViolationFromException(validationException)
            }
        }

        return ErrorDTO(
            code = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = validationException.message ?: "Validation error"
        )
    }

    private fun extractViolationFromException(validationException: ConstraintViolationException): String =
        validationException.constraintViolations.joinToString(separator = "--") {it.message};

}