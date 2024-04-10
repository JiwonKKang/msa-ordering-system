package com.food.ordering.system.payment.service.domain.event

import com.food.ordering.system.payment.service.domain.entity.Payment

class PaymentFailedEvent(
    payment: Payment,
    failureMessages: List<String>
) : PaymentEvent(
    payment = payment,
    failureMessages = failureMessages)  {
}