package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.valueobject.CustomerId

class Customer(
    customerId: CustomerId
) : AggregateRoot<CustomerId>(customerId)