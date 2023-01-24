package com.middleton.hotcoffees.feature.coffee_options.domain.usecases

import com.middleton.hotcoffees.feature.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.feature.coffee_options.domain.repository.CoffeeOptionsRepository
import javax.inject.Inject

class GetCoffeeByIdUseCase @Inject constructor(private val repository: CoffeeOptionsRepository) {
    suspend operator fun invoke(coffeeId: Int): Coffee {
        return repository.getCoffeeById(coffeeId)
    }
}