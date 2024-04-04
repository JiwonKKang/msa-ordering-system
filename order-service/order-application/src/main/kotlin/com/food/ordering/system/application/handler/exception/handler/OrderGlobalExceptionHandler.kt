package com.food.ordering.system.application.handler.exception.handler

import com.food.ordering.system.application.handler.ErrorDTO
import com.food.ordering.system.application.handler.GlobalExceptionHandler
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class OrderGlobalExceptionHandler : GlobalExceptionHandler() {

    @ExceptionHandler(OrderDomainException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDomainException(orderDomainException: OrderDomainException): ErrorDTO {
        return ErrorDTO(
            code = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = orderDomainException.message
        )
    }

    @ExceptionHandler(OrderNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleException(orderNotFoundException: OrderNotFoundException): ErrorDTO {
        return ErrorDTO(
            code = HttpStatus.NOT_FOUND.reasonPhrase,
            message = orderNotFoundException.message
        )
    }

}