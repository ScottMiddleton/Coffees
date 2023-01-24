package com.middleton.hotcoffees.feature.coffee_options.domain.usecases

import com.middleton.hotcoffees.feature.coffee_options.domain.repository.CoffeeOptionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCoffeesUseCase @Inject constructor(private val repository: CoffeeOptionsRepository) {
    suspend operator fun invoke(): Flow<Result<Unit>> {
        return repository.updateCoffees()
    }
}