package com.food.ordering.system.customer.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.food.ordering.system"])
class CustomerServiceApplication {

}

fun main(args: Array<String>) {
    runApplication<CustomerServiceApplication>(*args)
}