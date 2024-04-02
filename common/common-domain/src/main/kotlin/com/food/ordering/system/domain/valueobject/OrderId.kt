package com.food.ordering.system.domain.valueobject

import java.util.*

data class OrderId(
        val id: UUID
): BaseId<UUID>(id)
