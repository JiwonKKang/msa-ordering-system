package com.food.ordering.system.order.service.messaging.listener.kafka

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel
import com.food.ordering.system.kafka.order.avro.model.PaymentStatus
import com.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class PaymentResponseKafkaListener(
    private val paymentResponseMessageListener: PaymentResponseMessageListener,
    private val orderMessagingDataMapper: OrderMessagingDataMapper
) : KafkaConsumer<PaymentResponseAvroModel> {

    val log = logger()

    @KafkaListener(
        id = "\${kafka-consumer-config.payment-consumer-group-id}",
        topics = ["\${order-service.payment-response-topic-name}"])
    override fun receive(
        @Payload messages: List<PaymentResponseAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        log.info("""${messages.size} number of payment responses received with 
            keys: $keys, 
            partitions: $partitions, 
            offsets: $offsets
            """
        )
        messages.forEach { paymentResponseAvroModel ->
            val paymentResponse = orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel)
            log.info("Processing ${paymentResponseAvroModel.paymentStatus.name} payment for order id: ${paymentResponseAvroModel.orderId}")
            if (paymentResponseAvroModel.paymentStatus == PaymentStatus.COMPLETED) {
                paymentResponseMessageListener.paymentCompleted(paymentResponse)
            } else {
                paymentResponseMessageListener.paymentCancelled(paymentResponse)
            }
        }
    }

}
