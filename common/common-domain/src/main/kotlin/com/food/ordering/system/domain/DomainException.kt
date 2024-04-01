package com.food.ordering.system.domain

open class DomainException(
    override val message: String = ""
) : RuntimeException() {
}