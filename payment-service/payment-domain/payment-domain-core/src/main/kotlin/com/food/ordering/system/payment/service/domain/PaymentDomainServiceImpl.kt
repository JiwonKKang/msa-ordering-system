package com.food.ordering.system.payment.service.domain

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.PaymentStatus
import com.food.ordering.system.payment.service.domain.entity.CreditEntry
import com.food.ordering.system.payment.service.domain.entity.CreditHistory
import com.food.ordering.system.payment.service.domain.entity.Payment
import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent
import com.food.ordering.system.payment.service.domain.event.PaymentEvent
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent
import com.food.ordering.system.payment.service.domain.valueobject.TransactionType

class PaymentDomainServiceImpl: PaymentDomainService {

    val log = logger()

    override fun validateAndInitiatePayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>,
        paymentCompletedEventPublisher: DomainEventPublisher<PaymentCompletedEvent>,
        paymentFailedEventPublisher: DomainEventPublisher<PaymentFailedEvent>
    ): PaymentEvent {

        payment.validatePayment(failureMessages)
        validateCreditEntry(payment, creditEntry, failureMessages)
        creditEntry.totalCreditAmount.subtract(payment.price)
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT)
        validateCreditHistory(creditEntry, creditHistories, failureMessages)

        return when {
            failureMessages.isEmpty() -> {
                payment.updateStatus(PaymentStatus.COMPLETED)
                PaymentCompletedEvent(
                    payment = payment,
                    paymentCompletedEventPublisher = paymentCompletedEventPublisher
                )
            }
            else -> {
                payment.updateStatus(PaymentStatus.FAILED)
                PaymentFailedEvent(
                    payment = payment,
                    failureMessages = failureMessages,
                    paymentFailedEventPublisher = paymentFailedEventPublisher
                )
            }
        }
    }

    override fun validateAndCancelPayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>,
        paymentCancelledEventPublisher: DomainEventPublisher<PaymentCancelledEvent>,
        paymentFailedEventPublisher: DomainEventPublisher<PaymentFailedEvent>
    ): PaymentEvent {
        payment.validatePayment(failureMessages)
        creditEntry.totalCreditAmount.add(payment.price)
        updateCreditHistory(payment, creditHistories, TransactionType.CREDIT)
        return when {
            failureMessages.isEmpty() -> {
                payment.updateStatus(PaymentStatus.CANCELLED)
                PaymentCancelledEvent(
                    payment = payment,
                    paymentCancelledEventPublisher = paymentCancelledEventPublisher
                )
            }
            else -> {
                payment.updateStatus(PaymentStatus.FAILED)
                PaymentFailedEvent(
                    payment = payment,
                    failureMessages = failureMessages,
                    paymentFailedEventPublisher = paymentFailedEventPublisher
                )
            }
        }
    }

    private fun validateCreditHistory(
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>
    ) {
        val totalCreditHistory = getTotalCreditHistory(creditHistories, TransactionType.CREDIT)
        val totalDebitHistory = getTotalCreditHistory(creditHistories, TransactionType.DEBIT)

        if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
            log.error("Customer with id: ${creditEntry.customerId.id} has more debit than credit")
            failureMessages.add("Customer with id: ${creditEntry.customerId.id} has more debit than credit")
        }

        if (creditEntry.totalCreditAmount != totalCreditHistory.subtract(totalDebitHistory)) {
            log.error("Customer with id: ${creditEntry.customerId.id} has incorrect credit amount")
            failureMessages.add("Customer with id: ${creditEntry.customerId.id} has incorrect credit amount")
        }
    }

    private fun getTotalCreditHistory(creditHistories: List<CreditHistory>,
                                      transactionType: TransactionType): Money =
        creditHistories.stream()
            .filter { it.transactionType == transactionType }
            .map(CreditHistory::amount)
            .reduce(Money.ZERO, Money::add)

    private fun updateCreditHistory(
        payment: Payment,
        creditHistories: MutableList<CreditHistory>,
        transactionType: TransactionType
    ) {
        creditHistories.add(
            CreditHistory(
                customerId = payment.customerId,
                amount = payment.price,
                transactionType = transactionType
            )
        )
    }

    private fun validateCreditEntry(payment: Payment,
                                    creditEntry: CreditEntry,
                                    failureMessages: MutableList<String>) {

        log.info("totalCreditAmount = ${creditEntry.totalCreditAmount}")
        log.info("payment price = ${payment.price.amount}")

        if (payment.price.isGreaterThan(creditEntry.totalCreditAmount)) {
            log.error("Customer with id: ${payment.customerId.id} Payment amount is greater than credit entry amount")
            failureMessages.add("Customer with id: ${payment.customerId.id} Payment amount is greater than credit entry amount")
        }
    }
}