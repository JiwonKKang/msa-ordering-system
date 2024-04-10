package com.food.ordering.system.payment.service.domain.ports.out.persistance

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.payment.service.domain.entity.CreditHistory

interface CreditHistoryJpaPort {

    fun save(creditHistory: CreditHistory)

    fun findByCustomerId(customerId: CustomerId): MutableList<CreditHistory>
}