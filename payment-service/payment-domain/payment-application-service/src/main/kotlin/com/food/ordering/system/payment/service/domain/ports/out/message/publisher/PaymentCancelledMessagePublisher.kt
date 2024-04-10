package com.food.ordering.system.payment.service.domain.ports.out.message.publisher

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent

interface PaymentCancelledMessagePublisher : DomainEventPublisher<PaymentCancelledEvent> {
}