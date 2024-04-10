package com.food.ordering.system.payment.service.domain.valueobject

import com.food.ordering.system.domain.valueobject.BaseId
import java.util.*

data class CreditEntryId(
    val id: UUID
) : BaseId<UUID>(id) {
}