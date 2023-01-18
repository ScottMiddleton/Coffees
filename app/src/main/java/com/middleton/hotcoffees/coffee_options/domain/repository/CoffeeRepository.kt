package com.middleton.hotcoffees.coffee_options.domain.repository

import com.middleton.hotcoffees.coffee_options.domain.model.Coffee

interface CoffeeRepository {
    suspend fun getCoffees(): Result<List<Coffee>>
    suspend fun getCoffeeById(coffeeId: Int): Coffee
}