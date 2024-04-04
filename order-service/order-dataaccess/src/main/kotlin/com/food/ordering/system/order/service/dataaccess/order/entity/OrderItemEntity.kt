package com.food.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@IdClass(OrderItemEntityId::class)
@Table(name = "order_item")
class OrderItemEntity(
    @Id
    val id: Long,
    val productId: UUID,
    val quantity: Int,
    val price: BigDecimal,
    val subTotal: BigDecimal,
    ) {
    @Id
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id")
    lateinit var order: OrderEntity;

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderItemEntity) return false

        if (productId != other.productId) return false
        if (quantity != other.quantity) return false
        if (price != other.price) return false
        if (subTotal != other.subTotal) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + productId.hashCode()
        result = 31 * result + quantity
        result = 31 * result + price.hashCode()
        result = 31 * result + subTotal.hashCode()
        result = 31 * result + order.hashCode()
        return result
    }
}
