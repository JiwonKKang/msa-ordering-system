package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.valueobject.*
import com.food.ordering.system.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress
import com.food.ordering.system.order.service.domain.valueobject.TrackingId
import java.util.*

class Order(
    orderId: OrderId = OrderId(UUID.randomUUID()),
    val customerId: CustomerId,
    val restaurantId: RestaurantId,
    val deliveryAddress: StreetAddress,
    val price: Money,
    val items: List<OrderItem>,
    var trackingId: TrackingId = TrackingId(UUID.randomUUID()),
    var orderStatus: OrderStatus = OrderStatus.PENDING,
    var failureMessages: MutableList<String> = mutableListOf()
): AggregateRoot<OrderId>(orderId) {

    fun initializeOrder() {
        initializeOrderItems()
    }

    fun validateOrder() {
        validateTotalPrice()
        validateItemsPrice()
    }

    fun pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw OrderDomainException("Order is not in correct state for pay operation!")
        }
    }

    fun approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw OrderDomainException("Order is not in correct state for approve operation!")
        }
        orderStatus = OrderStatus.APPROVED
    }

    fun initCancel(failureMessages: List<String>) {
        if (orderStatus != OrderStatus.PAID) {
            throw OrderDomainException("Order is not in correct state for initCancel initialization!")
        }
        orderStatus = OrderStatus.CANCELLING
        updateFailureMessages(failureMessages)
    }

    fun cancel(failureMessages: List<String>) {
        if (!(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING)) {
            throw OrderDomainException("Order is not in correct state for cancel operation!")
        }
        orderStatus = OrderStatus.CANCELLED
        updateFailureMessages(failureMessages)
    }

    private fun updateFailureMessages(failureMessages: List<String>) {
        this.failureMessages.addAll(failureMessages.stream().filter{ message -> message.isNotEmpty() }.toList())
    }

    private fun validateItemsPrice() {
        val orderItemTotal: Money = items.stream().map { orderItem ->
            validateItemPrice(orderItem)
            orderItem.subTotal
        }.reduce(Money.ZERO, Money::add)

        if (price != orderItemTotal) {
            throw OrderDomainException(
                "Total price: ${price.amount} is not equal to Order items total: ${orderItemTotal.amount}!"
            )
        }
    }

    private fun validateItemPrice(orderItem: OrderItem?) {
        orderItem?.let {
            with(orderItem) {
                if (!isPriceValid()) {
                    throw OrderDomainException("Order item price: ${price.amount} is not valid for product ${product.id!!.value}")
                }
            }
        }
    }


    private fun validateTotalPrice() {
        if (!price.isGreaterThanZero()) {
            throw OrderDomainException("Total price must be greater than zero!")
        }
    }

    private fun initializeOrderItems() {
        var itemId = 1L;
        items.forEach { it.initializeOrderItem(OrderItemId(itemId++), super.id ) }
    }


}