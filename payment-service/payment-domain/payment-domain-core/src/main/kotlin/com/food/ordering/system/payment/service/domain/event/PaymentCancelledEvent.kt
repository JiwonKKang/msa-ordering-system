package com.food.ordering.system.payment.service.domain.event

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.payment.service.domain.entity.Payment

class PaymentCancelledEvent(
    payment: Payment,
    private val paymentCancelledEventPublisher: DomainEventPublisher<PaymentCancelledEvent>
) : PaymentEvent(payment = payment)  {

    override fun fire() {
        paymentCancelledEventPublisher.publish(this)
    }
}