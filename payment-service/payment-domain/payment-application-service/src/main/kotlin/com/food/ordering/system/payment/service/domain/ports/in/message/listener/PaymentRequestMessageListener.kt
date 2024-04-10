package com.food.ordering.system.payment.service.domain.ports.`in`.message.listener

import com.food.ordering.system.payment.service.domain.dto.PaymentRequest

interface PaymentRequestMessageListener {

    fun completePayment(paymentRequest: PaymentRequest)

    fun cancelPayment(paymentRequest: PaymentRequest)

}