package com.food.ordering.system.order.service.domain.dto.create

import com.food.ordering.system.domain.valueobject.OrderStatus
import java.util.*

data class CreateOrderResponse(
    val orderTrackingId: UUID,
    val orderStatus: OrderStatus,
    val message: String,
)
