package com.food.ordering.system.payment.service.domain

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest
import com.food.ordering.system.payment.service.domain.event.PaymentEvent
import com.food.ordering.system.payment.service.domain.ports.`in`.message.listener.PaymentRequestMessageListener
import com.food.ordering.system.payment.service.domain.ports.out.message.publisher.PaymentCancelledMessagePublisher
import com.food.ordering.system.payment.service.domain.ports.out.message.publisher.PaymentCompletedMessagePublisher
import com.food.ordering.system.payment.service.domain.ports.out.message.publisher.PaymentFailedMessagePublisher
import org.springframework.stereotype.Service

@Service
class PaymentRequestMessageListenerImpl(
    private val paymentRequestHelper: PaymentRequestHelper,
    private val paymentCompletedMessagePublisher: PaymentCompletedMessagePublisher,
    private val paymentCancelledMessagePublisher: PaymentCancelledMessagePublisher,
    private val paymentFailedMessagePublisher: PaymentFailedMessagePublisher
) : PaymentRequestMessageListener {

    val log = logger()

    override fun completePayment(paymentRequest: PaymentRequest) {
        val paymentEvent = paymentRequestHelper.persistPayment(paymentRequest)
        fireEvent(paymentEvent)
    }

    override fun cancelPayment(paymentRequest: PaymentRequest) {
        val paymentEvent = paymentRequestHelper.persistCancelPayment(paymentRequest)
        fireEvent(paymentEvent)
    }

    private fun fireEvent(paymentEvent: PaymentEvent) {
        log.info("")
        paymentEvent.fire()
    }
}