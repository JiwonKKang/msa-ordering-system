package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.mapper.OrderMapper
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderCreateCommandHandler(
    private val orderCreateHelper: OrderCreateHelper,
    private val orderMapper: OrderMapper,
    private val eventPublisher: OrderCreatedPaymentRequestMessagePublisher
) {

    val log = logger()

    @Transactional
    fun createOrder(createOrderCommand: CreateOrderCommand): CreateOrderResponse {
        val orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand)
        log.info("Order is created with id : ${orderCreatedEvent.order.id?.id}")
        eventPublisher.publish(orderCreatedEvent)
        return orderMapper.orderToCreateOrderResponse(orderCreatedEvent.order, "Order Created Successfully")
    }
}