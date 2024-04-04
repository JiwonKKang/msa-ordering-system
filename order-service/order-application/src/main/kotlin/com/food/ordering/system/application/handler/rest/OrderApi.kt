package com.food.ordering.system.application.handler.rest

import com.food.ordering.system.domain.util.logger
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.ports.input.service.OrderUseCase
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/order"], produces = ["application/vnd.api.v1+json"])
class OrderApi(
    private val orderUseCase: OrderUseCase,
) {

    val log = logger()

    @PostMapping("/create")
    fun createOrder(@RequestBody createOrderCommand: CreateOrderCommand): CreateOrderResponse {
        log.info("Creating order for customer: ${createOrderCommand.customerId} at restaurant: ${createOrderCommand.restaurantId}")
        val createOrder = orderUseCase.createOrder(createOrderCommand)
        log.info("Order is created with tracking id : ${createOrder.orderTrackingId}")
        return createOrder
    }

    @GetMapping("/{trackingId}")
    fun trackOrder(@PathVariable trackingId: UUID): TrackOrderResponse {
        val trackOrderQuery = TrackOrderQuery(trackingId)
        val trackOrderResponse = orderUseCase.trackOrder(trackOrderQuery)
        log.info("Returning order status with tracking id: ${trackOrderResponse.orderTrackingId}")
        return trackOrderResponse
    }

}