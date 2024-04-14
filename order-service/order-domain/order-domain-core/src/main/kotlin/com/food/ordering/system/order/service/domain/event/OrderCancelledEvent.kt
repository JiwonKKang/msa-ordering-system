package com.food.ordering.system.order.service.domain.event

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.order.service.domain.entity.Order
import java.time.ZonedDateTime

class OrderCancelledEvent(
    order: Order,
    createdAt: ZonedDateTime,
    private val orderCancelledEventPublisher: DomainEventPublisher<OrderCancelledEvent>
) : OrderEvent(order, createdAt) {
    override fun fire() {
        orderCancelledEventPublisher.publish(this)
    }
}