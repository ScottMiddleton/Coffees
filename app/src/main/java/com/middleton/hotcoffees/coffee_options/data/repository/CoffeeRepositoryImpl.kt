package com.middleton.hotcoffees.coffee_options.data.repository

import com.middleton.hotcoffees.coffee_options.data.local.CoffeeDao
import com.middleton.hotcoffees.coffee_options.data.local.CoffeeUserInteraction
import com.middleton.hotcoffees.coffee_options.data.remote.CoffeeApi
import com.middleton.hotcoffees.coffee_options.domain.mappers.toCoffee
import com.middleton.hotcoffees.coffee_options.domain.mappers.toCoffeeEntity
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(
    private val api: CoffeeApi,
    private val dao: CoffeeDao
) : CoffeeRepository {
    override suspend fun getCoffees(): Flow<List<Coffee>> {
        return dao.getAllCoffees().map { entityList -> entityList.map { it.toCoffee() } }
    }

    override suspend fun updateCoffees(): Result<Unit> {
        return try {
            dao.insertAll(api.getCoffees().map { it.toCoffeeEntity() })
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCoffeeById(coffeeId: Int): Coffee {
        return dao.getCoffeeById(coffeeId).toCoffee()
    }

    override suspend fun updateCoffeeLikedStatus(coffeeId: Int, isLiked: Boolean) {
        val currentUserInteraction = dao.getCoffeeById(coffeeId).userInteraction

        val updatedUserInteraction: CoffeeUserInteraction =
            currentUserInteraction?.copy(isLiked = isLiked)
                ?: CoffeeUserInteraction(coffeeId, isLiked)

        dao.updateUserInteraction(updatedUserInteraction)
    }
}