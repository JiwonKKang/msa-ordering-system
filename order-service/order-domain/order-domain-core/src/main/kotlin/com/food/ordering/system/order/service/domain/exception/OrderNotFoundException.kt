package com.food.ordering.system.order.service.domain.exception

import com.food.ordering.system.domain.exception.DomainException

class OrderNotFoundException(
    message: String
) : DomainException(message) {
}