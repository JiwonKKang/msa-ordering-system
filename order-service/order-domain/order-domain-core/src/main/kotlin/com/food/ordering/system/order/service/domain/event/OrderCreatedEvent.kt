package com.food.ordering.system.order.service.domain.event

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.order.service.domain.entity.Order
import java.time.ZonedDateTime

class OrderCreatedEvent(
    order: Order,
    createdAt: ZonedDateTime,
    private val orderCreatedEventPublisher: DomainEventPublisher<OrderCreatedEvent>
) : OrderEvent(order, createdAt) {
    override fun fire() {
        orderCreatedEventPublisher.publish(this)
    }
}