package com.food.ordering.system.payment.service.domain

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.payment.service.domain.entity.CreditEntry
import com.food.ordering.system.payment.service.domain.entity.CreditHistory
import com.food.ordering.system.payment.service.domain.entity.Payment
import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent
import com.food.ordering.system.payment.service.domain.event.PaymentEvent
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent

interface PaymentDomainService {

    fun validateAndInitiatePayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>,
        paymentCompletedEventPublisher: DomainEventPublisher<PaymentCompletedEvent>,
        paymentFailedEventPublisher: DomainEventPublisher<PaymentFailedEvent>
    ): PaymentEvent

    fun validateAndCancelPayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>,
        paymentCancelledEventPublisher: DomainEventPublisher<PaymentCancelledEvent>,
        paymentFailedEventPublisher: DomainEventPublisher<PaymentFailedEvent>
    ): PaymentEvent



}


