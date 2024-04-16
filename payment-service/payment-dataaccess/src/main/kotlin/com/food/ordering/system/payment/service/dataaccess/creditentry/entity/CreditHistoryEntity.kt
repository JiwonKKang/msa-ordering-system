package com.food.ordering.system.payment.service.dataaccess.creditentry.entity

import com.food.ordering.system.payment.service.domain.valueobject.TransactionType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.math.BigDecimal
import java.util.*

@Entity
class CreditHistoryEntity(
    @Id
    val id: UUID,

    val customerId: UUID,

    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType
    ) {
}