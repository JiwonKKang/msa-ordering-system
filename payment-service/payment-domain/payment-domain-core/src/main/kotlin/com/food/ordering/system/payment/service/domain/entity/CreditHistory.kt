package com.food.ordering.system.payment.service.domain.entity

import com.food.ordering.system.domain.entity.BaseEntity
import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.domain.valueobject.Money
import com.food.ordering.system.payment.service.domain.valueobject.TransactionType
import com.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId
import java.util.*

class CreditHistory(
    creditHistoryId: CreditHistoryId = CreditHistoryId(UUID.randomUUID()),
    val customerId: CustomerId = CustomerId(UUID.randomUUID()),
    val amount: Money = Money.ZERO,
    val transactionType: TransactionType = TransactionType.CREDIT,
) : BaseEntity<CreditHistoryId>(creditHistoryId) {



}