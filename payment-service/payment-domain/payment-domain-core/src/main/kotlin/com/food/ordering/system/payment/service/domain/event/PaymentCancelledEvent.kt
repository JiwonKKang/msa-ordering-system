package com.food.ordering.system.payment.service.domain.event

import com.food.ordering.system.payment.service.domain.entity.Payment

class PaymentCancelledEvent(
    payment: Payment
) : PaymentEvent(payment = payment)  {
}