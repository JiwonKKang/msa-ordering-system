package com.food.ordering.system.payment.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.payment.service.domain.valueobject.CreditEntryId
import java.util.*

class CreditEntry(
    creditEntryId: CreditEntryId = CreditEntryId(UUID.randomUUID()),
    val customerId: CustomerId = CustomerId(UUID.randomUUID()),
    val totalCreditAmount: Money = Money.ZERO
) : BaseEntity<CreditEntryId>(creditEntryId) {

    fun addCreditAmount(amount: Money) {
        this.totalCreditAmount.add(amount)
    }

    fun subtractCreditAmount(amount: Money) {
        this.totalCreditAmount.subtract(amount)
    }
}