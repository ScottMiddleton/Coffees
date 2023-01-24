package com.middleton.hotcoffees.coffee_options.presentation.detail

import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeOptionsRepository
import javax.inject.Inject

class UpdateCoffeeLikedStatusUseCase @Inject constructor(private val repository: CoffeeOptionsRepository) {

    suspend operator fun invoke(coffeeId: Int, isLiked: Boolean) {
        repository.updateCoffeeLikedStatus(coffeeId, isLiked)
    }

}
