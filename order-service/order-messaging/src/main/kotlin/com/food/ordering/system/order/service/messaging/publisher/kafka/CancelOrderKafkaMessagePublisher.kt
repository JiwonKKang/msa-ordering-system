package com.food.ordering.system.order.service.messaging.publisher.kafka

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.kafka.config.producer.service.KafkaProducer
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.springframework.stereotype.Component

@Component
class CancelOrderKafkaMessagePublisher(
    private val orderServiceConfigData: OrderServiceConfigData,
    private val orderMessagingDataMapper: OrderMessagingDataMapper,
    private val kafkaProducer: KafkaProducer<String, PaymentRequestAvroModel>,
    private val orderKafkaMessageHelper: OrderKafkaMessageHelper
) : OrderCancelledPaymentRequestMessagePublisher{

    val log = logger()

    override fun publish(domainEvent: OrderCancelledEvent) {
        val orderId = domainEvent.order.id.value.toString()
        log.info("Received OrderCreatedEvent for OrderId: $orderId")

        try {
            val paymentRequestAvroModel =
                orderMessagingDataMapper.orderCancelledEventToPaymentRequestAvroModel(domainEvent)

            kafkaProducer.send(
                topicName = orderServiceConfigData.paymentRequestTopicName,
                key = orderId,
                message = paymentRequestAvroModel,
                orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.paymentResponseTopicName,
                    paymentRequestAvroModel,
                    orderId,
                    "PaymentRequestAvroModel"
                )
            )
            log.info("PaymentRequestAvroModel send to Kafka for order id: ${paymentRequestAvroModel.getOrderId()}");
        } catch (e: Exception) {
            log.error("Error while sending PaymentRequestAvroModel message" +
                    "to kafka with order id : $orderId error: ${e.message}")
        }
    }
}

