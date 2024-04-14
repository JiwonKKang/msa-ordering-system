package com.food.ordering.system.order.service.domain.event

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.order.service.domain.entity.Order
import java.time.ZonedDateTime

class OrderPaidEvent(
    order: Order,
    createdAt: ZonedDateTime,
    private val orderPaidEventPublisher: DomainEventPublisher<OrderPaidEvent>
) : OrderEvent(order, createdAt) {
    override fun fire() {
        orderPaidEventPublisher.publish(this)
    }
}