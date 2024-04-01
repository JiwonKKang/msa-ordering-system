package com.food.ordering.system.domain.entity

abstract class BaseEntity<ID>(val id: ID) {

    fun setId(id: ID) {
    }

    fun getId(): ID {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseEntity<*>) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}