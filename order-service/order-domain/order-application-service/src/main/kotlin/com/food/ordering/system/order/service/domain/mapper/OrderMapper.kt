package com.food.ordering.system.order.service.domain.mapper

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.ProductId
import com.food.ordering.system.domain.valueobject.RestaurantId
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.OrderItem
import com.food.ordering.system.order.service.domain.entity.Product
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress
import org.springframework.stereotype.Component
import java.util.*

@Component
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

    fun orderToCreateOrderResponse(order: Order, message: String): CreateOrderResponse {
        return CreateOrderResponse(
            orderTrackingId = order.trackingId.value,
            orderStatus = order.orderStatus,
            message = message
        )
    }

    fun orderToTrackOrderResponse(order: Order): TrackOrderResponse {
        return TrackOrderResponse(
            orderTrackingId = order.trackingId.value,
            orderStatus = order.orderStatus,
            failureMessages = order.failureMessages
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



}