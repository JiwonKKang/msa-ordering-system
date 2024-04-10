package com.food.ordering.system.payment.service.domain.ports.out.persistance

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.payment.service.domain.entity.CreditEntry

interface CreditEntryJpaPort {

    fun save(creditEntry: CreditEntry)

    fun findByCustomerId(customerId: CustomerId): CreditEntry
}