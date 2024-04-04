package com.food.ordering.system.order.service.dataaccess.order.adapter

import com.food.ordering.system.domain.valueobject.OrderId
import com.food.ordering.system.order.service.dataaccess.order.mapper.OrderMapper
import com.food.ordering.system.order.service.dataaccess.order.mapper.orderEntityToOrder
import com.food.ordering.system.order.service.dataaccess.order.repository.OrderJpaRepository
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderJpaPort
import com.food.ordering.system.order.service.domain.valueobject.TrackingId
import org.springframework.stereotype.Component

@Component
class OrderJpaAdapter(
    private val orderJpaRepository: OrderJpaRepository,
    private val orderMapper: OrderMapper
) : OrderJpaPort {
    override fun save(order: Order): Order =
        orderJpaRepository.save(orderMapper.orderToOrderEntity(order))
                .orderEntityToOrder()

    override fun findByTrackingId(trackingId: TrackingId): Order? =
        orderJpaRepository.findByOrderTrackingId(trackingId.value)
                ?.orderEntityToOrder()

    override fun findById(orderId: OrderId): Order? =
        orderJpaRepository.findById(orderId.value)
                .orElseThrow { OrderNotFoundException("not find order id : ${orderId.value}") }
                .orderEntityToOrder()
}