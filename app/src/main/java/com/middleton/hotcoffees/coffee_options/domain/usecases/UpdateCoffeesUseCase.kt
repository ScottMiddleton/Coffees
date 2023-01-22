package com.middleton.hotcoffees.coffee_options.domain.usecases

import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeRepository
import javax.inject.Inject

class UpdateCoffeesUseCase @Inject constructor(private val repository: CoffeeRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.updateCoffees()
    }
}