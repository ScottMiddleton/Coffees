package com.middleton.hotcoffees.coffee_options.data.repository

import com.middleton.hotcoffees.coffee_options.data.local.CoffeeDao
import com.middleton.hotcoffees.coffee_options.data.remote.CoffeeApi
import com.middleton.hotcoffees.coffee_options.domain.mappers.toCoffee
import com.middleton.hotcoffees.coffee_options.domain.mappers.toCoffeeEntity
import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeRepository
import java.io.IOException
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(
    private val api: CoffeeApi,
    private val dao: CoffeeDao
) : CoffeeRepository {
    override suspend fun getCoffees(): Result<List<Coffee>> {
        return try {
            val coffeeDtos = api.getCoffees()
            dao.insertAll(coffeeDtos.map { it.toCoffeeEntity() })
            Result.success(coffeeDtos.map { it.toCoffee() })
        } catch (e: Exception) {
            val coffeeEntities = dao.getAllCoffees()
            if (coffeeEntities.isEmpty()) {
                Result.failure(e)
            } else {
                Result.success(coffeeEntities.map { it.toCoffee() })
            }
        }
    }

    override suspend fun getCoffeeById(coffeeId: Int): Coffee {
        return dao.getCoffeeById(coffeeId).toCoffee()
    }
}