package com.food.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "order_address")
class OrderAddressEntity(
    @Id
    val id: UUID,
    val street: String,
    val postalCode: String,
    val city: String,
) {
    @OneToOne
    @JoinColumn(name = "order_id")
    lateinit var order: OrderEntity
}
