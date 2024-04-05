package com.food.ordering.system.order.service.messaging.publisher.kafka

import com.food.ordering.system.domain.util.logger
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.function.BiConsumer

@Component
class OrderKafkaMessageHelper {

    val log = logger()

    fun <T> getKafkaCallback(
        responseTopicName: String,
        requestAvroModel: T,
        orderId: String,
        requestAvroModelName: String
    ): BiConsumer<SendResult<String, T>, Throwable?> {

        return BiConsumer { result, ex ->
            if (ex == null) {
                val metadata = result.recordMetadata
                log.info("order id : $orderId " +
                        "Topic: ${metadata.topic()} " +
                        "Partition: ${metadata.partition()}" +
                        "Offset: ${metadata.offset()}" +
                        "Timestamp: ${metadata.timestamp()}")
            } else {
                log.error("Error while sending $requestAvroModelName " +
                        "message ${requestAvroModel.toString()} to topic $responseTopicName", ex)
            }
        }
    }

}