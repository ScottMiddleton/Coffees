package com.middleton.hotcoffees.coffee_options.data.repository

import com.middleton.hotcoffees.coffee_options.data.remote.CoffeeApi
import com.middleton.hotcoffees.coffee_options.domain.mappers.toCoffee
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeRepository
import java.io.IOException
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(private val api: CoffeeApi): CoffeeRepository {
    override suspend fun getCoffees(): Result<List<Coffee>> {
        return try {
            Result.success(api.getCoffees().map { it.toCoffee() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override fun getCoffeeById(coffeeId: Int): Coffee {
        // TODO implement the local db
        return Coffee(
            id = 1,
            title = "Latte",
            description = "A latte is a coffee-based drink made with espresso and steamed milk",
            ingredients = listOf("espresso", "steamed milk", "foamed milk"),
            imageUrl = "https://example.com/latte.jpg",
            liked = true
        )
    }
}