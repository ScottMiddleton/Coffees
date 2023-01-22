package com.middleton.hotcoffees.coffee_options.domain.repository

import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {
    suspend fun getCoffees(): Flow<List<Coffee>>

    suspend fun updateCoffees(): Result<Unit>
    suspend fun getCoffeeById(coffeeId: Int): Coffee

    suspend fun updateCoffeeLikedStatus(coffeeId: Int, isLiked: Boolean)
}