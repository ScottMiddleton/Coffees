package com.middleton.hotcoffees.domain.repository

import com.middleton.hotcoffees.data.remote.CoffeeApi
import com.middleton.hotcoffees.domain.mappers.toCoffee
import com.middleton.hotcoffees.domain.model.Coffee
import java.io.IOException
import javax.inject.Inject

class CoffeeRepository @Inject constructor(private val api: CoffeeApi) {
    suspend fun getCoffees(): Result<List<Coffee>> {
        return try {
            Result.success(api.getCoffees().map { it.toCoffee() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}