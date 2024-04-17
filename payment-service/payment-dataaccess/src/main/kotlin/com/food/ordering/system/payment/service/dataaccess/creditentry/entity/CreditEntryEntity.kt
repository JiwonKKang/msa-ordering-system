package com.food.ordering.system.payment.service.dataaccess.creditentry.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.util.*

@Entity
class CreditEntryEntity (
    @Id
    val id: UUID,
    val customerId: UUID,
    val totalCreditEntry: BigDecimal
) {
}