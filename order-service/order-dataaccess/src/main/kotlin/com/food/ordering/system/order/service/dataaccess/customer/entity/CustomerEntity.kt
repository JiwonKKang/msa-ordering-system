package com.food.ordering.system.order.service.dataaccess.customer.entity

import com.food.ordering.system.domain.valueobject.CustomerId
import com.food.ordering.system.order.service.domain.entity.Customer
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "order_customer_m_view", schema = "customer")
class CustomerEntity(
    @Id
    val id: UUID
)

fun CustomerEntity.customerEntityToCustomer() = Customer(CustomerId(this.id))