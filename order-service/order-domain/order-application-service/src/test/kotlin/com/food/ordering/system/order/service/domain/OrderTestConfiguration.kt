package com.food.ordering.system.order.service.domain

import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantApprovalMessagePublisher
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerJpaPort
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderJpaPort
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantJpaPort
import org.mockito.Mockito
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackages = ["com.food.ordering.system"])
class OrderTestConfiguration {

    @Bean
    fun orderCreatedPaymentRequestMessagePublisher(): OrderCreatedPaymentRequestMessagePublisher {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher::class.java)
    }

    @Bean
    fun orderCancelledPaymentRequestMessagePublisher(): OrderCancelledPaymentRequestMessagePublisher {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher::class.java)
    }

    @Bean
    fun orderPaidRestaurantApprovalMessagePublisher(): OrderPaidRestaurantApprovalMessagePublisher {
        return Mockito.mock(OrderPaidRestaurantApprovalMessagePublisher::class.java)
    }

    @Bean
    fun orderJpaPort(): OrderJpaPort {
        return Mockito.mock(OrderJpaPort::class.java)
    }

    @Bean
    fun customerJpaPort(): CustomerJpaPort {
        return Mockito.mock(CustomerJpaPort::class.java)
    }

    @Bean
    fun restaurantJpaPort(): RestaurantJpaPort {
        return Mockito.mock(RestaurantJpaPort::class.java)
    }

    @Bean
    fun orderDomainService(): OrderDomainService {
        return OrderDomainServiceImpl()
    }

}
