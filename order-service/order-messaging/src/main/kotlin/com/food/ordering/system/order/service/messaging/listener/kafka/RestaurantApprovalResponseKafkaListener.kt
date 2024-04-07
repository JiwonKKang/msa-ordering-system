package com.food.ordering.system.order.service.messaging.listener.kafka

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.springframework.kafka.annotation.KafkaListener

class RestaurantApprovalResponseKafkaListener(
    private val restaurantApprovalResponseMessageListener: RestaurantApprovalResponseMessageListener,
    private val orderMessagingDataMapper: OrderMessagingDataMapper
) : KafkaConsumer<RestaurantApprovalResponseAvroModel> {

    val log = logger()

    @KafkaListener(id = "\${kafka-consumer-config.restaurant-approval-consumer-group-id}",
        topics = ["\${order-service.restaurant-approval-response-topic-name}"])
    override fun receive(
        messages: List<RestaurantApprovalResponseAvroModel>,
        keys: List<String>,
        partitions: List<Int>,
        offsets: List<Long>
    ) {
        log.info("""${messages.size} number of restaurant approval responses received with 
            keys: $keys, 
            partitions: $partitions, 
            offsets: $offsets
            """
        )

        messages.forEach { restaurantApprovalResponseAvroModel ->
            val restaurantApprovalResponse = orderMessagingDataMapper
                .restaurantApprovalRequestAvroModelToRestaurantApprovalResponse(restaurantApprovalResponseAvroModel)
            log.info("Processing ${restaurantApprovalResponseAvroModel.orderApprovalStatus} restaurant approval for order id: ${restaurantApprovalResponseAvroModel.orderId}")
            if (restaurantApprovalResponseAvroModel.orderApprovalStatus == OrderApprovalStatus.APPROVED) {
                restaurantApprovalResponseMessageListener.orderApproved(restaurantApprovalResponse)
            } else {
                restaurantApprovalResponseMessageListener.orderRejected(restaurantApprovalResponse)
            }
        }
    }
}