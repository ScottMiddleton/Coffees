package com.middleton.hotcoffees.coffee_options.domain.usecases

import com.middleton.hotcoffees.coffee_options.domain.model.Coffee
import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeOptionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCoffeesSortedUseCase @Inject constructor(private val repository: CoffeeOptionsRepository) {
    suspend operator fun invoke(): Flow<List<Coffee>> {
        return repository.getCoffees().map {
            it.sortedByDescending { coffee -> coffee.liked }
        }
    }
}