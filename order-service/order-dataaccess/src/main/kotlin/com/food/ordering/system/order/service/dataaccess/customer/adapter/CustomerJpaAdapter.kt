package com.food.ordering.system.order.service.dataaccess.customer.adapter

import com.food.ordering.system.order.service.dataaccess.customer.entity.customerEntityToCustomer
import com.food.ordering.system.order.service.dataaccess.customer.repository.CustomerRepository
import com.food.ordering.system.order.service.domain.entity.Customer
import com.food.ordering.system.order.service.domain.exception.CustomerNotFoundException
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerJpaPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerJpaAdapter(
    private val customerRepository: CustomerRepository
) : CustomerJpaPort{

    override fun findCustomer(customerId: UUID): Customer =
        customerRepository.findById(customerId)
            .orElseThrow { CustomerNotFoundException("customer not found id : $customerId") }
            .customerEntityToCustomer()
}