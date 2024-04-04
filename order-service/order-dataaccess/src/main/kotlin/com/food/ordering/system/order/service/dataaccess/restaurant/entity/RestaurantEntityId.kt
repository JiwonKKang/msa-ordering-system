package com.food.ordering.system.order.service.dataaccess.restaurant.entity

import java.io.Serializable
import java.util.*


data class RestaurantEntityId(
    val restaurantId: UUID,
    val productId: UUID
) : Serializable