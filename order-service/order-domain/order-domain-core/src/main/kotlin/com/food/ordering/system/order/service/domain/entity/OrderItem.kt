package com.food.ordering.system.order.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId

class OrderItem(
    orderItemId: OrderItemId = OrderItemId(1L),
    val product: Product,
    val quantity: Int,
    val price: Money,
    val subTotal: Money,
): BaseEntity<OrderItemId>(orderItemId) {
    lateinit var orderId: OrderId

    constructor(
        orderItemId: OrderItemId,
        orderId: OrderId,
        product: Product,
        quantity: Int,
        price: Money,
        subTotal: Money
    ) : this(orderItemId, product, quantity, price, subTotal) {
        this.orderId = orderId
    }

    fun initializeOrderItem(orderItemId: OrderItemId, orderId: OrderId) {
        super.id = orderItemId
        this.orderId = orderId
    }

    fun isPriceValid(): Boolean{
        return price.isGreaterThanZero() &&
                price == product.price &&
                price.multiply(quantity) == subTotal
    }
}