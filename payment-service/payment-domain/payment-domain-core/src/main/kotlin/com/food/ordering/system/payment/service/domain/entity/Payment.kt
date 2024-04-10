package com.food.ordering.system.payment.service.domain.entity

import com.food.ordering.system.domain.entity.AggregateRoot
import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.domain.valueobject.PaymentStatus
import com.food.ordering.system.payment.service.domain.valueobject.PaymentId
import java.time.ZonedDateTime
import java.util.*

class Payment(
    paymentId: PaymentId = PaymentId(UUID.randomUUID()),
    val orderId: OrderId = OrderId(UUID.randomUUID()),
    val customerId: CustomerId = CustomerId(UUID.randomUUID()),
    val price: Money = Money.ZERO,
    var paymentStatus: PaymentStatus = PaymentStatus.COMPLETED,
    val createdAt: ZonedDateTime = ZonedDateTime.now()
) : AggregateRoot<PaymentId>(paymentId) {

    fun validatePayment(failureMessages: MutableList<String>) {
        if (!price.isGreaterThanZero()) {
            failureMessages.add("Price must be greater than zero!")
        }
    }

    fun updateStatus(paymentStatus: PaymentStatus) {
        this.paymentStatus = paymentStatus
    }


}