package com.food.ordering.system.order.service.domain.exception

import com.food.ordering.system.domain.DomainException

class OrderNotFoundException(
    override val message: String = ""
) : DomainException() {
}