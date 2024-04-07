package com.food.ordering.system.order.service.dataaccess.order.repository

import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderJpaRepository : JpaRepository<OrderEntity, UUID> {

    fun findByTrackingId(orderTrackingId: UUID): OrderEntity?

}