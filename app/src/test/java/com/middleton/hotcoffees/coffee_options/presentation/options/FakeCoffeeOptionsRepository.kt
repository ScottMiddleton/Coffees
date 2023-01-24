package com.middleton.hotcoffees.coffee_options.presentation.options

import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeOptionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class FakeCoffeeOptionsRepository : CoffeeOptionsRepository {

    private var coffeeList: List<Coffee> = emptyList()
    var isUpdateSuccessful = true

    val getCoffeesFlow = MutableStateFlow<List<Coffee>>(emptyList())

    override suspend fun getCoffees(): Flow<List<Coffee>> {
        return getCoffeesFlow
    }

    override suspend fun updateCoffees(): Flow<Result<Unit>> = flow {
        if (isUpdateSuccessful) {
            getCoffeesFlow.value = provideCoffees()
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(Error()))
        }
    }

    override suspend fun getCoffeeById(coffeeId: Int): Coffee {
        return coffeeList.find { it.id == coffeeId }!!
    }

    override suspend fun updateCoffeeLikedStatus(coffeeId: Int, isLiked: Boolean) {
        coffeeList.map {
            if (it.id == coffeeId) {
                it.copy(liked = isLiked)
            } else {
                it
            }
        }
    }

    fun provideCoffees(): List<Coffee> {
        return listOf(
            Coffee(
                1,
                "Espresso",
                "A strong, concentrated coffee made by forcing hot water through finely ground coffee beans.",
                listOf("Coffee Beans"),
                "https://example.com/espresso.jpg",
                false
            ),
            Coffee(
                2,
                "Latte",
                "A coffee drink made with espresso and steamed milk, topped with a layer of foam.",
                listOf("Espresso", "Milk", "Foam"),
                "https://example.com/latte.jpg",
                false
            ),
            Coffee(
                3,
                "Cappuccino",
                "A coffee drink made with equal parts espresso, steamed milk, and foam.",
                listOf("Espresso", "Milk", "Foam"),
                "https://example.com/cappuccino.jpg",
                false
            )
        )
    }
}
