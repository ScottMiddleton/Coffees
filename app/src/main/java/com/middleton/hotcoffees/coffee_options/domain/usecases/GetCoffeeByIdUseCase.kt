package com.middleton.hotcoffees.coffee_options.domain.usecases

import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeRepository
import javax.inject.Inject

class GetCoffeeByIdUseCase @Inject constructor(private val repository: CoffeeRepository) {
    suspend operator fun invoke(coffeeId: Int): Coffee {
        return repository.getCoffeeById(coffeeId)
    }
}