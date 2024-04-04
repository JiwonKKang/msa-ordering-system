package com.food.ordering.system.order.service.dataaccess.order.entity

import com.food.ordering.system.domain.valueobject.OrderStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "order")
class OrderEntity(
    @Id
    val id: UUID,
    val customerId: UUID,
    val restaurantId: UUID,
    val orderTrackingId: UUID,
    val price: BigDecimal,

    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus,
    val failureMessage: String,

    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderAddress: OrderAddressEntity,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: List<OrderItemEntity> = mutableListOf()


) {

}