package com.food.ordering.system.kafka.config.producer.service.impl

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.kafka.config.producer.service.KafkaProducer
import jakarta.annotation.PreDestroy
import org.apache.avro.specific.SpecificRecordBase
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.function.BiConsumer

@Component
class KafkaProducerImpl<K : Serializable, V : SpecificRecordBase>(
    private val kafkaTemplate: KafkaTemplate<K, V>
) : KafkaProducer<K, V> {
    private val log = logger()

    @PreDestroy
    fun close() {
        log.info("Closing kafka producer")
        kafkaTemplate.destroy()
    }

    override fun send(topicName: String, key: K, message: V, callBack: BiConsumer<SendResult<K, V>, Throwable?>) {

    }

}