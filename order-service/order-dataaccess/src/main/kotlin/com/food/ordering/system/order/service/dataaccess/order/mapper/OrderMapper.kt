package com.food.ordering.system.order.service.dataaccess.order.mapper

import com.food.ordering.system.domain.valueobject.*
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderAddressEntity
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderItemEntity
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.OrderItem
import com.food.ordering.system.order.service.domain.entity.Product
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress
import com.food.ordering.system.order.service.domain.valueobject.TrackingId
import org.springframework.stereotype.Component

@Component
class OrderMapper {

    fun orderToOrderEntity(order: Order): OrderEntity {
        val orderEntity = OrderEntity(
            id = order.id!!.value,
            customerId = order.customerId.value,
            restaurantId = order.restaurantId.value,
            orderTrackingId = order.trackingId.value,
            price = order.price.amount,
            orderStatus = order.orderStatus,
            failureMessage = order.failureMessages.joinToString(separator = ","),
            orderAddress = deliveryAddressToAddressEntity(order.deliveryAddress),
            orderItems = orderItemsToOrderItemEntities(order.items)
        )

        orderEntity.orderAddress.order = orderEntity
        orderEntity.orderItems.forEach { it.order = orderEntity }

        return orderEntity
    }

    private fun orderItemsToOrderItemEntities(items: List<OrderItem>): List<OrderItemEntity> = items.map { orderItem ->
        OrderItemEntity(
            id = orderItem.id!!.value,
            productId = orderItem.product.id!!.value,
            quantity = orderItem.quantity,
            price = orderItem.price.amount,
            subTotal = orderItem.subTotal.amount
        )
    }

    private fun deliveryAddressToAddressEntity(deliveryAddress: StreetAddress): OrderAddressEntity =
        OrderAddressEntity(
            id = deliveryAddress.id,
            street = deliveryAddress.street,
            postalCode = deliveryAddress.postalCode,
            city = deliveryAddress.city
        )

}

fun OrderEntity.orderEntityToOrder(): Order = let {
    Order(
        orderId = OrderId(it.id),
        customerId = CustomerId(it.customerId),
        restaurantId = RestaurantId(it.restaurantId),
        deliveryAddress = it.orderAddress.addressEntityToDeliveryAddress(),
        price = Money(amount = it.price),
        items = it.orderItems.orderItemEntitiesToOrderItems(),
        trackingId = TrackingId(it.orderTrackingId),
        failureMessages = if(it.failureMessage.isEmpty()) mutableListOf() else
            it.failureMessage.split(",").toMutableList(),
        orderStatus = orderStatus
    )
}

private fun OrderAddressEntity.addressEntityToDeliveryAddress(): StreetAddress = let {
    StreetAddress(
        id = it.id,
        street = it.street,
        postalCode = it.postalCode,
        city = it.city,
    )
}

private fun List<OrderItemEntity>.orderItemEntitiesToOrderItems(): List<OrderItem> = let {
    it.map { orderItemEntity ->
        OrderItem(
            orderItemId = OrderItemId(orderItemEntity.id),
            orderId = OrderId(orderItemEntity.order.id),
            price = Money(orderItemEntity.price),
            product = Product(ProductId(orderItemEntity.productId)),
            quantity = orderItemEntity.quantity,
            subTotal = Money(orderItemEntity.subTotal),
        )
    }
}