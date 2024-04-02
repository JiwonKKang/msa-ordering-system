package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.mapper.OrderMapper
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerJpaPort
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderJpaPort
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantJpaPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class OrderCreateCommandHandler(
    private val orderDomainService: OrderDomainService,
    private val customerJpaPort: CustomerJpaPort,
    private val restaurantJpaPort: RestaurantJpaPort,
    private val orderJpaPort: OrderJpaPort,
    private val orderMapper: OrderMapper
) {

    val log = logger()

    @Transactional
    fun createOrder(createOrderCommand: CreateOrderCommand) {
        checkCustomer(createOrderCommand.customerId)
        val restaurant = checkRestaurant(createOrderCommand)
        val order = orderMapper.createOrderCommandToOrder(createOrderCommand)
        val orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant)
        val saveOrder = saveOrder(order)
        log.info("Order is created with id : ${saveOrder.id?.id}")
        return orderMapper.orderToCreateOrderResponse(saveOrder)
    }

    private fun checkRestaurant(createOrderCommand: CreateOrderCommand): Restaurant {
        val restaurant = orderMapper.createOrderCommandToRestaurant(createOrderCommand)
        restaurantJpaPort.findRestaurantInformation(restaurant)
            ?: throw OrderDomainException("Could not find restaurant with restaurant id: ${createOrderCommand.restaurantId}")
    }

    private fun checkCustomer(customerId: UUID) {
        customerJpaPort.findCustomer(customerId) ?: throw OrderDomainException("customer not founded")
    }

    private fun saveOrder(order: Order): Order {
        return orderJpaPort.save(order) ?: throw OrderDomainException("Could not save order!")
    }
}