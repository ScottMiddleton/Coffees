package com.middleton.hotcoffees.domain.usecases

import com.middleton.hotcoffees.domain.model.Coffee
import com.middleton.hotcoffees.domain.repository.CoffeeRepository
import javax.inject.Inject
import kotlin.Result


class GetCoffeesUseCase @Inject constructor(private val repository: CoffeeRepository) {
    suspend operator fun invoke(): Result<List<Coffee>> {
        val result = repository.getCoffees()
        result.onSuccess { coffees ->
            return Result.success(coffees.sortedByDescending { it.liked })
        }
        return result
    }
}