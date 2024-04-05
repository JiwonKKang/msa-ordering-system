package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime

const val SEOUL = "Asia/Seoul"

@Service
class OrderDomainServiceImpl : OrderDomainService {

    private val log = logger();

    override fun validateAndInitiateOrder(order: Order, restaurant: Restaurant): OrderCreatedEvent {
        validateRestaurant(restaurant)
        setOrderProductInformation(order, restaurant)
        order.validateOrder()
        order.initializeOrder()
        log.info("Order with id: ${order.id.id} is initiated}")
        return OrderCreatedEvent(
            order = order,
            createdAt = ZonedDateTime.now(ZoneId.of(SEOUL))
        )

    }

    private fun setOrderProductInformation(order: Order, restaurant: Restaurant) {
        val productsMap = restaurant.products.associateBy { it.id }

        order.items.forEach { orderItem ->
            val restaurantProduct = productsMap[orderItem.product.id]

            restaurantProduct?.let {
                orderItem.product.updateWithConfirmedNameAndPrice(it.name, it.price)
            }
        }
    }

    private fun validateRestaurant(restaurant: Restaurant) {
        if (!restaurant.active) {
            throw OrderDomainException("Restaurant with id ${restaurant.id.id} is currently not active!")
        }
    }

    override fun payOrder(order: Order): OrderPaidEvent {
        order.pay()
        log.info("Order with id: ${order.id.id} is paid")
        return OrderPaidEvent(
            order = order,
            createdAt = ZonedDateTime.now(ZoneId.of(SEOUL))
        )
    }

    override fun approveOrder(order: Order) {
        order.approve()
        log.info("Order with id: ${order.id.id} is approved")
    }

    override fun cancelOrderPayment(order: Order, failureMessage: List<String>): OrderCancelledEvent {
        order.initCancel(failureMessage)
        log.info("Order payment is cancelling for order id: ${order.id.id}")
        return OrderCancelledEvent(
            order = order,
            createdAt = ZonedDateTime.now(ZoneId.of(SEOUL))
        )
    }

    override fun cancelOrder(order: Order, failureMessage: List<String>) {
        order.cancel(failureMessage)
        log.info("Order with id: ${order.id.id} is cancelled")
    }
}