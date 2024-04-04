package com.food.ordering.system.order.service.domain.ports.output.repository

import com.food.ordering.system.order.service.domain.entity.Customer
import java.util.*

interface CustomerJpaPort {

    fun findCustomer(customerId: UUID): Customer
}
