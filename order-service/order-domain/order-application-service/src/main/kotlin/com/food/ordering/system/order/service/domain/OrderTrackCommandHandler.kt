package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException
import com.food.ordering.system.order.service.domain.mapper.OrderMapper
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderJpaPort
import com.food.ordering.system.order.service.domain.valueobject.TrackingId
import org.springframework.stereotype.Component

@Component
class OrderTrackCommandHandler(
    private val orderMapper: OrderMapper,
    private val orderJpaPort: OrderJpaPort,

) {

    fun trackOrder(trackOrderQuery: TrackOrderQuery): TrackOrderResponse {
        return orderJpaPort.findByTrackingId(TrackingId(trackOrderQuery.orderTrackingId))
            ?.let { orderMapper.orderToTrackOrderResponse(it) }
            ?: throw OrderNotFoundException("Could not found order with track id: ${trackOrderQuery.orderTrackingId}")
    }
}