package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.mapper.OrderMapper
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerJpaPort
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderJpaPort
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantJpaPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class OrderCreateHelper(
    private val orderDomainService: OrderDomainService,
    private val orderJpaPort: OrderJpaPort,
    private val customerJpaPort: CustomerJpaPort,
    private val restaurantJpaPort: RestaurantJpaPort,
    private val orderMapper: OrderMapper,
    private val orderCreatedMessagePublisher: OrderCreatedPaymentRequestMessagePublisher
) {

    val log = logger()

    @Transactional
    fun persistOrder(createOrderCommand: CreateOrderCommand): OrderCreatedEvent {
        checkCustomer(createOrderCommand.customerId)
        val restaurant = checkRestaurant(createOrderCommand)
        val order = orderMapper.createOrderCommandToOrder(createOrderCommand)
        val orderCreatedEvent = orderDomainService.validateAndInitiateOrder(
            order = order,
            restaurant = restaurant,
            orderCreatedEventPublisher = orderCreatedMessagePublisher)
        saveOrder(order)
        log.info("Order is created with id : ${order.id.id}")
        return orderCreatedEvent;
    }

    private fun checkRestaurant(createOrderCommand: CreateOrderCommand): Restaurant {
        val restaurant = orderMapper.createOrderCommandToRestaurant(createOrderCommand)
        return restaurantJpaPort.findRestaurantInformation(restaurant);
    }

    private fun checkCustomer(customerId: UUID) {
        customerJpaPort.findCustomer(customerId);
    }

    private fun saveOrder(order: Order) {
        orderJpaPort.save(order);
    }
}