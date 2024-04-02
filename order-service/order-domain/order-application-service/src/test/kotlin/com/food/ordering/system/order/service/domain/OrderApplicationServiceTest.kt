package com.food.ordering.system.order.service.domain

import com.food.ordering.system.domain.valueobject.*
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.order.service.domain.dto.create.OrderItem
import com.food.ordering.system.order.service.domain.entity.Customer
import com.food.ordering.system.order.service.domain.entity.Order
import com.food.ordering.system.order.service.domain.entity.Product
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.mapper.OrderMapper
import com.food.ordering.system.order.service.domain.ports.input.service.OrderUseCase
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerJpaPort
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderJpaPort
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantJpaPort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [OrderTestConfiguration::class])
class OrderApplicationServiceTest {

     @Autowired
     private lateinit var orderUseCase: OrderUseCase

     @Autowired
     private lateinit var orderMapper: OrderMapper

     @Autowired
     private lateinit var orderJpaPort: OrderJpaPort

     @Autowired
     private lateinit var customerJpaPort: CustomerJpaPort

     @Autowired
     private lateinit var restaurantJpaPort: RestaurantJpaPort

    private lateinit var createOrderCommand: CreateOrderCommand
    private lateinit var createOrderCommandWrongPrice: CreateOrderCommand
    private lateinit var createOrderCommandWrongProductPrice: CreateOrderCommand
    private val CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41")
    private val RESTAURANT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45")
    private val PRODUCT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48")
    private val ORDER_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb")
    private val SAGA_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afa")
    private val PRICE: BigDecimal = BigDecimal("200.00")


    @BeforeAll
    fun init() {
        createOrderCommand = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            address = OrderAddress(
                street = "street_1",
                postalCode = "1000AB",
                city = "Paris"
            ),
            price = PRICE,
            items = listOf(
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 1,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("50.00")
                ),
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00")
                ),
            )
        )
        createOrderCommandWrongPrice = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            address = OrderAddress(
                street = "street_1",
                postalCode = "1000AB",
                city = "Paris"
            ),
            price = BigDecimal("250.00"),
            items = listOf(
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 1,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("50.00"),
                ),
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00"),
                ),
            )
        )

        createOrderCommandWrongProductPrice = CreateOrderCommand(
            customerId = CUSTOMER_ID,
            restaurantId = RESTAURANT_ID,
            address = OrderAddress(
                street = "street_1",
                postalCode = "1000AB",
                city = "Paris"
            ),
            price = BigDecimal("210.00"),
            items = listOf(
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 1,
                    price = BigDecimal("60.00"),
                    subTotal = BigDecimal("60.00"),
                ),
                OrderItem(
                    productId = PRODUCT_ID,
                    quantity = 3,
                    price = BigDecimal("50.00"),
                    subTotal = BigDecimal("150.00"),
                ),
            )
        )

        val customer: Customer = Customer(CustomerId(CUSTOMER_ID))
        val restaurantResponse: Restaurant = Restaurant(
            restaurantId = RestaurantId(createOrderCommand.restaurantId),
            products = listOf(
                Product(productId = ProductId(PRODUCT_ID), name = "product-1", price = Money(BigDecimal("50.00"))),
                Product(productId = ProductId(PRODUCT_ID), name = "product-2", price = Money(BigDecimal("50.00"))),
            ),
            active = true
        )
        val order: Order = orderMapper.createOrderCommandToOrder(createOrderCommand)
        order.id = OrderId(ORDER_ID)

        `when`(customerJpaPort.findCustomer(CUSTOMER_ID)).thenReturn(customer)

        `when`(
             restaurantJpaPort.findRestaurantInformation(
                orderMapper.createOrderCommandToRestaurant(createOrderCommand)
            )
        ).thenReturn(restaurantResponse)

        `when`(orderJpaPort.save(anyOrNull<Order>()))
            .thenReturn(order)

    }


    @Test
    fun `should create order successfully`() {
        val createOrderResponse = orderUseCase.createOrder(createOrderCommand)
        assertEquals(createOrderResponse.orderStatus, OrderStatus.PENDING)
        assertEquals(createOrderResponse.message, "Order Created Successfully")
        assertNotNull(createOrderResponse.orderTrackingId)

    }
}
