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
            if (!orderItem.isPriceValid()) {
                throw OrderDomainException("Order item price: ${orderItem.price.amount} is not valid for product ${orderItem.product.id.value}")
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
        items.forEach { it.initializeOrderItem(OrderItemId(itemId++), getId() ) }
    }


}