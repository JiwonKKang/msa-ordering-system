package com.food.ordering.system.order.service.domain.exception

import com.food.ordering.system.domain.exception.DomainException

class RestaurantNotFoundException(
    override val message: String = ""
) : DomainException(){
}