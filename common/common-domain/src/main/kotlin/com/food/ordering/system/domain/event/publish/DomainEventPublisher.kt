package com.food.ordering.system.domain.event.publish

import com.food.ordering.system.domain.event.DomainEvent

interface DomainEventPublisher<T : DomainEvent<*>> {

    fun publish(domainEvent: T)

}