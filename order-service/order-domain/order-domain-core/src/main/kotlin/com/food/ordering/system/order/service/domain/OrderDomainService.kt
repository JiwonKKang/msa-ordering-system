package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent

interface OrderDomainService {

    fun validateAndInitiateOrder(
        order: Order,
        restaurant: Restaurant,
        orderCreatedEventPublisher: DomainEventPublisher<OrderCreatedEvent>
    ): OrderCreatedEvent

    fun payOrder(order: Order,
                 orderPaidEventPublisher: DomainEventPublisher<OrderPaidEvent>
    ): OrderPaidEvent

    fun cancelOrderPayment(
        order: Order,
        failureMessage: List<String>,
        orderCancelledEventPublisher: DomainEventPublisher<OrderCancelledEvent>
    ): OrderCancelledEvent

    fun approveOrder(order: Order)

    fun cancelOrder(
        order: Order,
        failureMessage: List<String>
    )


}