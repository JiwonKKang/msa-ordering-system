package com.food.ordering.system.order.service.messaging.publisher.kafka

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.kafka.config.producer.service.KafkaProducer
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantApprovalMessagePublisher
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.springframework.stereotype.Component

@Component
class PayOrderKafkaMessagePublisher(
    private val orderServiceConfigData: OrderServiceConfigData,
    private val orderMessagingDataMapper: OrderMessagingDataMapper,
    private val kafkaProducer: KafkaProducer<String, RestaurantApprovalRequestAvroModel>,
    private val orderKafkaMessageHelper: OrderKafkaMessageHelper
) : OrderPaidRestaurantApprovalMessagePublisher {

    val log = logger()

    override fun publish(domainEvent: OrderPaidEvent) {
        val orderId = domainEvent.order.id.value.toString()
        log.info("Received OrderPaidEvent for OrderId: $orderId")

        try {
            val restaurantApprovalRequestAvroModel =
                orderMessagingDataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent)

            kafkaProducer.send(
                topicName = orderServiceConfigData.restaurantApprovalRequestTopicName,
                key = orderId,
                message = restaurantApprovalRequestAvroModel,
                orderKafkaMessageHelper.getKafkaCallback(
                    orderServiceConfigData.restaurantApprovalResponseTopicName,
                    restaurantApprovalRequestAvroModel,
                    orderId,
                    "RestaurantApprovalRequestAvroModel")
            )

            log.info("RestaurantApprovalRequestAvroModel send to Kafka for order id: $orderId")
        } catch (e: Exception) {
            log.error("Error while sending RestaurantApprovalRequestAvroModel message" +
                    "to kafka with order id : $orderId error: ${e.message}")
        }
    }
}