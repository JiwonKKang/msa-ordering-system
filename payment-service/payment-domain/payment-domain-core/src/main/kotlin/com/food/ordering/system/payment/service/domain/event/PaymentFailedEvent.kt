package com.food.ordering.system.payment.service.domain.event

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.payment.service.domain.entity.Payment

class PaymentFailedEvent(
    payment: Payment,
    failureMessages: List<String>,
    private val paymentFailedEventPublisher: DomainEventPublisher<PaymentFailedEvent>
) : PaymentEvent(
    payment = payment,
    failureMessages = failureMessages)  {

    override fun fire() {
        paymentFailedEventPublisher.publish(this)
    }
}