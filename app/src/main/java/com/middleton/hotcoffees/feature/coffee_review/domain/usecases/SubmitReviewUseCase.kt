package com.middleton.hotcoffees.feature.coffee_review.domain.usecases

import com.middleton.hotcoffees.feature.coffee_review.domain.model.Review
import com.middleton.hotcoffees.feature.coffee_review.domain.repository.CoffeeReviewRepository
import javax.inject.Inject

class SubmitReviewUseCase @Inject constructor(private val repository: CoffeeReviewRepository){
    suspend operator fun invoke(review: Review): Result<Unit> {
        return repository.submitReview(review)
    }
}