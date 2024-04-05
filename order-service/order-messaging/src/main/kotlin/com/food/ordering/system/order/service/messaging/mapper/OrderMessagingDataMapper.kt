package com.food.ordering.system.order.service.messaging.mapper

import com.food.ordering.system.kafka.order.avro.model.*
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderMessagingDataMapper {

    fun orderCreatedEventToPaymentRequestAvroModel(orderCreatedEvent: OrderCreatedEvent): PaymentRequestAvroModel {
        val order = orderCreatedEvent.order
        return PaymentRequestAvroModel.newBuilder()
            .setId(UUID.randomUUID())
            .setSagaId(UUID.randomUUID())
            .setCustomerId(order.customerId.value)
            .setOrderId(order.id.value)
            .setPrice(order.price.amount)
            .setCreatedAt(orderCreatedEvent.createdAt.toInstant())
            .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
            .build()

    }

    fun orderCancelledEventToPaymentRequestAvroModel(orderCancelledEvent: OrderCancelledEvent): PaymentRequestAvroModel {
        val order = orderCancelledEvent.order
        return PaymentRequestAvroModel.newBuilder()
            .setId(UUID.randomUUID())
            .setSagaId(UUID.randomUUID())
            .setCustomerId(order.customerId.value)
            .setOrderId(order.id.value)
            .setPrice(order.price.amount)
            .setCreatedAt(orderCancelledEvent.createdAt.toInstant())
            .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
            .build()

    }

    fun orderPaidEventToRestaurantApprovalRequestAvroModel(orderPaidEvent: OrderPaidEvent): RestaurantApprovalRequestAvroModel {
        val order = orderPaidEvent.order
        return RestaurantApprovalRequestAvroModel.newBuilder()
            .setId(UUID.randomUUID())
            .setSagaId(UUID.randomUUID())
            .setOrderId(order.id.value)
            .setRestaurantId(order.restaurantId.value)
            .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
            .setProducts(order.items.map {
                Product.newBuilder()
                    .setId(it.id.value.toString())
                    .setQuantity(it.quantity)
                    .build()
            })
            .setPrice(order.price.amount)
            .setCreatedAt(orderPaidEvent.createdAt.toInstant())
            .build()
    }

}