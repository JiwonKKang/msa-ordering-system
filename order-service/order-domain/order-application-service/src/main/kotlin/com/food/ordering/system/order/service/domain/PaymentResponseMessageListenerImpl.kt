package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener
import org.springframework.stereotype.Service

@Service
class PaymentResponseMessageListenerImpl(

) : PaymentResponseMessageListener {
    override fun paymentCompleted(paymentResponse: PaymentResponse) {
        TODO("Not yet implemented")
    }

    override fun paymentCancelled(paymentResponse: PaymentResponse) {
        TODO("Not yet implemented")
    }
}