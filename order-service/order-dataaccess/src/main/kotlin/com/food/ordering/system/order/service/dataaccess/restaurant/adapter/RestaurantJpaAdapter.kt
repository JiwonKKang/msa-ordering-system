package com.food.ordering.system.order.service.dataaccess.restaurant.adapter

import com.food.ordering.system.order.service.dataaccess.restaurant.mapper.RestaurantMapper
import com.food.ordering.system.order.service.dataaccess.restaurant.repository.RestaurantRepository
import com.food.ordering.system.order.service.domain.entity.Restaurant
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantJpaPort
import org.springframework.stereotype.Component

@Component
class RestaurantJpaAdapter(
    private val restaurantRepository: RestaurantRepository,
    private val restaurantMapper: RestaurantMapper
) : RestaurantJpaPort {


    override fun findRestaurantInformation(restaurant: Restaurant): Restaurant {
        val restaurantProducts = restaurantMapper.restaurantToRestaurantProducts(restaurant)

        val restaurantEntities =
            restaurantRepository.findByRestaurantIdAndProductIdIn(restaurant.id.value, restaurantProducts)

        return restaurantMapper.restaurantEntitiesToRestaurant(restaurantEntities)
    }
}