package com.middleton.hotcoffees.coffee_options.domain.usecases

import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeRepository
import javax.inject.Inject

class GetCoffeesUseCase @Inject constructor(private val repository: CoffeeRepository) {
    suspend operator fun invoke(): Result<List<Coffee>> {
        val result = repository.getCoffees()
        result.onSuccess { coffees ->
            return Result.success(coffees.sortedByDescending { it.liked })
        }
        return result
    }
}