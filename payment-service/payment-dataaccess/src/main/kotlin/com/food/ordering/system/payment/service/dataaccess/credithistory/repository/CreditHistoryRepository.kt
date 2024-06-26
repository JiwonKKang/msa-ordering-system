package com.food.ordering.system.payment.service.dataaccess.credithistory.repository

import com.food.ordering.system.payment.service.dataaccess.credithistory.entity.CreditHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CreditHistoryRepository : JpaRepository<CreditHistoryEntity, UUID>{
    fun findByCustomerId(customerId: UUID): MutableList<CreditHistoryEntity>
}