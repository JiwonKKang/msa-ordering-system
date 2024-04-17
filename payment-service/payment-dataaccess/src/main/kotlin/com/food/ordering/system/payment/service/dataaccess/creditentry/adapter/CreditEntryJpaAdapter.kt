package com.food.ordering.system.payment.service.dataaccess.creditentry.adapter

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.payment.service.dataaccess.creditentry.exception.CreditEntryDataAccessException
import com.food.ordering.system.payment.service.dataaccess.creditentry.mapper.toCreditEntry
import com.food.ordering.system.payment.service.dataaccess.creditentry.mapper.toCreditEntryEntity
import com.food.ordering.system.payment.service.dataaccess.creditentry.repository.CreditEntryRepository
import com.food.ordering.system.payment.service.domain.entity.CreditEntry
import com.food.ordering.system.payment.service.domain.ports.out.persistance.CreditEntryJpaPort
import org.springframework.stereotype.Component

@Component
class CreditEntryJpaAdapter(
    private val creditEntryRepository: CreditEntryRepository
) : CreditEntryJpaPort {

    override fun save(creditEntry: CreditEntry) {
        creditEntryRepository.save(creditEntry.toCreditEntryEntity())
    }

    override fun findByCustomerId(customerId: CustomerId): CreditEntry =
        creditEntryRepository.findById(customerId.value)
            .orElseThrow { CreditEntryDataAccessException("not find credit entry id : ${customerId.value}") }
            .toCreditEntry()
}