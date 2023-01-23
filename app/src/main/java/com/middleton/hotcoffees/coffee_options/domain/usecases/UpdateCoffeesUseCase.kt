package com.middleton.hotcoffees.coffee_options.domain.usecases

import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCoffeesUseCase @Inject constructor(private val repository: CoffeeRepository) {
    suspend operator fun invoke(): Flow<Result<Unit>> {
        return repository.updateCoffees()
    }
}