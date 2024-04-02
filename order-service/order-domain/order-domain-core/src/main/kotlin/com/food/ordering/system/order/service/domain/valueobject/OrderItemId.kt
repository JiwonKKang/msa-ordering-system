package com.food.ordering.system.order.service.domain.valueobject

import com.food.ordering.system.domain.valueobject.BaseId

class OrderItemId(
    val id: Long,
) : BaseId<Long>(id)