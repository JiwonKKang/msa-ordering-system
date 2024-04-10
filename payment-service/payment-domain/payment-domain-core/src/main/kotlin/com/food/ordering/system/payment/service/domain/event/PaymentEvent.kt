package com.food.ordering.system.payment.service.domain.event

import com.food.ordering.system.domain.KOREA_DATE_TIME
import com.food.ordering.system.domain.event.DomainEvent
import com.food.ordering.system.payment.service.domain.entity.Payment
import java.time.ZoneId
import java.time.ZonedDateTime

abstract class PaymentEvent(
    val payment: Payment,
    val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of(KOREA_DATE_TIME)),
    val failureMessages: List<String> = listOf()
) : DomainEvent<Payment>{
}