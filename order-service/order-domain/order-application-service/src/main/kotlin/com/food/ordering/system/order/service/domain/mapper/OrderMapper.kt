package com.food.ordering.system.order.service.domain.mapper

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.ProductId
import com.food.ordering.system.domain.valueobject.RestaurantId
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.OrderItem
import com.food.ordering.system.order.service.domain.entity.Product
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress
import java.util.*

class OrderMapper {

    fun createOrderCommandToRestaurant(createOrderCommand: CreateOrderCommand): Restaurant {
        return Restaurant(
            restaurantId = RestaurantId(createOrderCommand.restaurantId),
            products = createOrderCommand.items.map { Product(ProductId(it.productId)) }
        )
    }

    fun createOrderCommandToOrder(createOrderCommand: CreateOrderCommand): Order {
        return Order(
            customerId = CustomerId(createOrderCommand.customerId),
            restaurantId = RestaurantId(createOrderCommand.restaurantId),
            deliveryAddress = streetAddressToDeliveryAddress(createOrderCommand.address),
            price = Money(createOrderCommand.price),
            items = orderItemsToOrderItemEntities(createOrderCommand.items)
        )
    }

    private fun orderItemsToOrderItemEntities(
        items: List<com.food.ordering.system.order.service.domain.dto.create.OrderItem>,
    ): List<OrderItem> {
        return items.map { orderItem ->
            OrderItem(
                product = Product(ProductId(orderItem.productId)),
                price = Money(orderItem.price),
                subTotal = Money(orderItem.subTotal),
                quantity = orderItem.quantity
            )
        }
    }

    private fun streetAddressToDeliveryAddress(address: OrderAddress): StreetAddress {
        return StreetAddress(
            id = UUID.randomUUID(),
            street = address.street,
            postalCode = address.postalCode,
            city = address.city
        )
    }

    fun orderToCreateOrderResponse(saveOrder: Order): CreateOrderResponse {
        return CreateOrderResponse(
            orderTrackingId = saveOrder.trackingId.value,
            orderStatus = saveOrder.orderStatus,
            message = saveOrder.failureMessages[0]
        )
    }

}