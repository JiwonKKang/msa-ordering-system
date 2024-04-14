package com.food.ordering.system.payment.service.domain.event

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.payment.service.domain.entity.Payment

class PaymentCompletedEvent(
    payment: Payment,
    private val paymentCompletedEventPublisher: DomainEventPublisher<PaymentCompletedEvent>
) : PaymentEvent(
    payment = payment)  {

    override fun fire() {
        paymentCompletedEventPublisher.publish(this)
    }
}