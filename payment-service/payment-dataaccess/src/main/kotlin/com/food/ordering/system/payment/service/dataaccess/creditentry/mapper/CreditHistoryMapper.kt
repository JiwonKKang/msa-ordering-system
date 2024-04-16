package com.food.ordering.system.payment.service.dataaccess.creditentry.mapper

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.payment.service.dataaccess.creditentry.entity.CreditHistoryEntity
import com.food.ordering.system.payment.service.domain.entity.CreditHistory
import com.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId

fun CreditHistory.toCreditHistoryEntity(): CreditHistoryEntity = run {
    CreditHistoryEntity(
        id = id.value,
        customerId = customerId.value,
        amount = amount.amount,
        transactionType = transactionType
    )
}

fun CreditHistoryEntity.toCreditHistory(): CreditHistory = run {
    CreditHistory(
        creditHistoryId = CreditHistoryId(id),
        customerId = CustomerId(customerId),
        amount = Money(amount),
        transactionType = transactionType
    )
}
