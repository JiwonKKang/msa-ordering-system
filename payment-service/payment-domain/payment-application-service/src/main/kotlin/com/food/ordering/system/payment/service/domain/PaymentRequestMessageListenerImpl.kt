package com.food.ordering.system.payment.service.domain

import com.food.ordering.system.payment.service.domain.dto.PaymentRequest
import com.food.ordering.system.payment.service.domain.ports.`in`.message.listener.PaymentRequestMessageListener

class PaymentRequestMessageListenerImpl : PaymentRequestMessageListener {
    override fun completePayment(paymentRequest: PaymentRequest) {
        TODO("Not yet implemented")
    }

    override fun cancelPayment(paymentRequest: PaymentRequest) {
        TODO("Not yet implemented")
    }
}