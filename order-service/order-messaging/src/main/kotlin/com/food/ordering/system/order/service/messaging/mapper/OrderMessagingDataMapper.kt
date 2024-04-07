package com.food.ordering.system.order.service.messaging.mapper

import com.food.ordering.system.kafka.order.avro.model.*
import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse
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

    fun paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel: PaymentResponseAvroModel): PaymentResponse {
        return PaymentResponse(
            id = paymentResponseAvroModel.id.toString(),
            sagaId = paymentResponseAvroModel.sagaId.toString(),
            orderId = paymentResponseAvroModel.orderId.toString(),
            paymentId = paymentResponseAvroModel.paymentId.toString(),
            paymentStatus = com.food.ordering.system.domain.valueobject.PaymentStatus.valueOf(
                paymentResponseAvroModel.paymentStatus.name),
            customerId = paymentResponseAvroModel.customerId.toString(),
            price = paymentResponseAvroModel.price,
            createdAt = paymentResponseAvroModel.createdAt,
            failureMessages = paymentResponseAvroModel.failureMessages
        )
    }

    fun restaurantApprovalRequestAvroModelToRestaurantApprovalResponse(restaurantApprovalResponseAvroModel: RestaurantApprovalResponseAvroModel): RestaurantApprovalResponse {
        return RestaurantApprovalResponse(
            id = restaurantApprovalResponseAvroModel.id.toString(),
            sagaId = restaurantApprovalResponseAvroModel.sagaId.toString(),
            orderId = restaurantApprovalResponseAvroModel.orderId.toString(),
            restaurant = restaurantApprovalResponseAvroModel.restaurantId.toString(),
            orderApprovalStatus = com.food.ordering.system.domain.valueobject.OrderApprovalStatus.valueOf(
                restaurantApprovalResponseAvroModel.orderApprovalStatus.name),
            createdAt = restaurantApprovalResponseAvroModel.createdAt,
            failureMessages = restaurantApprovalResponseAvroModel.failureMessages
        )
    }

}