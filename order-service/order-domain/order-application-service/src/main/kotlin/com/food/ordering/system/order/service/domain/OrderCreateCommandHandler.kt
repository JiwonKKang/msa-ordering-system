package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.mapper.OrderMapper
import org.springframework.stereotype.Component

@Component
class OrderCreateCommandHandler(
    private val orderCreateHelper: OrderCreateHelper,
    private val orderMapper: OrderMapper
) {

    val log = logger()

    fun createOrder(createOrderCommand: CreateOrderCommand): CreateOrderResponse {
        val orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand)
        log.info("Order is created with id : ${orderCreatedEvent.order.id.id}")
        orderCreatedEvent.fire()
        return orderMapper.orderToCreateOrderResponse(orderCreatedEvent.order, "Order Created Successfully")
    }
}