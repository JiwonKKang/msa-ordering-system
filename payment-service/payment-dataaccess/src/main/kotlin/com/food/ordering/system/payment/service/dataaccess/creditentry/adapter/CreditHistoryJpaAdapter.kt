package com.food.ordering.system.payment.service.dataaccess.creditentry.adapter

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.payment.service.dataaccess.creditentry.mapper.toCreditHistory
import com.food.ordering.system.payment.service.dataaccess.creditentry.mapper.toCreditHistoryEntity
import com.food.ordering.system.payment.service.dataaccess.creditentry.repository.CreditHistoryRepository
import com.food.ordering.system.payment.service.domain.entity.CreditHistory
import com.food.ordering.system.payment.service.domain.ports.out.persistance.CreditHistoryJpaPort

class CreditHistoryJpaAdapter(
    private val creditHistoryRepository: CreditHistoryRepository
) : CreditHistoryJpaPort {

    override fun save(creditHistory: CreditHistory) {
        creditHistoryRepository.save(creditHistory.toCreditHistoryEntity())
    }

    override fun findByCustomerId(customerId: CustomerId): MutableList<CreditHistory> =
        creditHistoryRepository
            .findByCustomerId(customerId.value)
            .map {it.toCreditHistory()}
            .toMutableList()

}