package com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval

import com.food.ordering.system.domain.event.publish.DomainEventPublisher
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent

interface OrderPaidRestaurantApprovalMessagePublisher : DomainEventPublisher<OrderPaidEvent> {
}