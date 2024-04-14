package com.food.ordering.system.payment.service.domain

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest
import com.food.ordering.system.payment.service.domain.entity.CreditEntry
import com.food.ordering.system.payment.service.domain.entity.CreditHistory
import com.food.ordering.system.payment.service.domain.entity.Payment
import com.food.ordering.system.payment.service.domain.event.PaymentEvent
import com.food.ordering.system.payment.service.domain.mapper.PaymentDataMapper
import com.food.ordering.system.payment.service.domain.ports.out.message.publisher.PaymentCancelledMessagePublisher
import com.food.ordering.system.payment.service.domain.ports.out.message.publisher.PaymentCompletedMessagePublisher
import com.food.ordering.system.payment.service.domain.ports.out.message.publisher.PaymentFailedMessagePublisher
import com.food.ordering.system.payment.service.domain.ports.out.persistance.CreditEntryJpaPort
import com.food.ordering.system.payment.service.domain.ports.out.persistance.CreditHistoryJpaPort
import com.food.ordering.system.payment.service.domain.ports.out.persistance.PaymentJpaPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class PaymentRequestHelper(
    private val paymentDomainService: PaymentDomainService,
    private val paymentDataMapper: PaymentDataMapper,
    private val paymentJpaPort: PaymentJpaPort,
    private val creditEntryJpaPort: CreditEntryJpaPort,
    private val creditHistoryJpaPort: CreditHistoryJpaPort,
    private val paymentCompletedMessagePublisher: PaymentCompletedMessagePublisher,
    private val paymentCancelledMessagePublisher: PaymentCancelledMessagePublisher,
    private val paymentFailedMessagePublisher: PaymentFailedMessagePublisher
) {

    val log = logger()

    @Transactional
    fun persistPayment(paymentRequest: PaymentRequest): PaymentEvent {
        log.info("Received payment complete event for order id: ${paymentRequest.orderId}")
        val payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest)
        val creditEntry = getCreditEntry(payment.customerId)
        val creditHistories = getCreditHistories(payment.customerId)
        val failureMessages = mutableListOf<String>()

        val paymentEvent =
            paymentDomainService.validateAndInitiatePayment(
                payment = payment,
                creditEntry = creditEntry,
                creditHistories = creditHistories,
                failureMessages = failureMessages,
                paymentCompletedEventPublisher = paymentCompletedMessagePublisher,
                paymentFailedEventPublisher = paymentFailedMessagePublisher)

        persistDbObjects(
            payment = payment,
            failureMessages = failureMessages,
            creditEntry = creditEntry,
            creditHistories = creditHistories
        )

        return paymentEvent
    }

    @Transactional
    fun persistCancelPayment(paymentRequest: PaymentRequest): PaymentEvent {
        log.info("Received payment rollback event for order id: ${paymentRequest.orderId}")
        val payment = paymentJpaPort.findByOrderId(OrderId(UUID.fromString(paymentRequest.orderId)))


        val creditEntry = getCreditEntry(payment.customerId)
        val creditHistories = getCreditHistories(payment.customerId)
        val failureMessages = mutableListOf<String>()

        val paymentEvent = paymentDomainService.validateAndCancelPayment(
            payment = payment,
            creditEntry = creditEntry,
            creditHistories = creditHistories,
            failureMessages = failureMessages,
            paymentCancelledEventPublisher = paymentCancelledMessagePublisher,
            paymentFailedEventPublisher = paymentFailedMessagePublisher
        )

        persistDbObjects(
            payment = payment,
            failureMessages = failureMessages,
            creditEntry = creditEntry,
            creditHistories = creditHistories,
        )

        return paymentEvent
    }

    private fun getCreditEntry(customerId: CustomerId): CreditEntry =
        creditEntryJpaPort.findByCustomerId(customerId)

    private fun getCreditHistories(customerId: CustomerId): MutableList<CreditHistory> =
        creditHistoryJpaPort.findByCustomerId(customerId)

    private fun persistDbObjects(
        payment: Payment,
        failureMessages: MutableList<String>,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>
    ) {
        paymentJpaPort.save(payment)
        if (failureMessages.isEmpty()) {
            creditEntryJpaPort.save(creditEntry)
            creditHistoryJpaPort.save(creditHistories[creditHistories.size - 1])
        }
    }



}