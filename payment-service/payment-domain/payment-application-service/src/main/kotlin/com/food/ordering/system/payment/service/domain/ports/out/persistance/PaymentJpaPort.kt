package com.food.ordering.system.payment.service.domain.ports.out.persistance

import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.payment.service.domain.entity.Payment

interface PaymentJpaPort {

    fun save(payment: Payment)

    fun findByOrderId(orderId: OrderId): Payment
}