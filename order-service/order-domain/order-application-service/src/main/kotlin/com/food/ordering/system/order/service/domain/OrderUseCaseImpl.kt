package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.ports.input.service.OrderUseCase
import org.springframework.stereotype.Service

@Service
class OrderUseCaseImpl(
    private val orderCreateCommandHandler: OrderCreateCommandHandler,
    private val orderTrackCommandHandler: OrderTrackCommandHandler,
) : OrderUseCase {

    override fun createOrder(createOrderCommand: CreateOrderCommand): CreateOrderResponse {
        return orderCreateCommandHandler.createOrder(createOrderCommand)
    }

    override fun trackOrder(trackOrderQuery: TrackOrderQuery): TrackOrderResponse {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery)
    }
}
