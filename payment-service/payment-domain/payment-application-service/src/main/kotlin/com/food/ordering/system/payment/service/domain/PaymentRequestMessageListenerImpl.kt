package com.food.ordering.system.payment.service.domain

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest
import com.food.ordering.system.payment.service.domain.event.PaymentEvent
import com.food.ordering.system.payment.service.domain.ports.`in`.message.listener.PaymentRequestMessageListener
import org.springframework.stereotype.Service

@Service
class PaymentRequestMessageListenerImpl(
    private val paymentRequestHelper: PaymentRequestHelper,
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
        log.info("Firing event: $paymentEvent")
        paymentEvent.fire()
    }
}