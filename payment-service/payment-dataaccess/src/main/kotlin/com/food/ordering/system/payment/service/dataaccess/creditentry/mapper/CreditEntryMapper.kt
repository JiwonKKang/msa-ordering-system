package com.food.ordering.system.payment.service.dataaccess.creditentry.mapper

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.payment.service.dataaccess.creditentry.entity.CreditEntryEntity
import com.food.ordering.system.payment.service.domain.entity.CreditEntry
import com.food.ordering.system.payment.service.domain.valueobject.CreditEntryId

fun CreditEntry.toCreditEntryEntity() : CreditEntryEntity =
    CreditEntryEntity(
        id = id.value,
        customerId = customerId.value,
        totalCreditEntry = totalCreditAmount.amount
    )

fun CreditEntryEntity.toCreditEntry() : CreditEntry =
    CreditEntry(
        creditEntryId = CreditEntryId(id),
        customerId = CustomerId(customerId),
        totalCreditAmount = Money(totalCreditEntry)
    )