package com.middleton.hotcoffees.coffee_options.presentation.detail

import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeRepository
import javax.inject.Inject

class UpdateCoffeeLikedStatusUseCase @Inject constructor(private val repository: CoffeeRepository) {

    suspend operator fun invoke(coffeeId: Int, isLiked: Boolean) {
        repository.updateCoffeeLikedStatus(coffeeId, isLiked)
    }

}