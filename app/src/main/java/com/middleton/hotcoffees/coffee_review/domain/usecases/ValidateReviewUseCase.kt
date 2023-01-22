package com.middleton.hotcoffees.coffee_review.domain.usecases

import com.middleton.hotcoffees.coffee_review.domain.model.Review
import javax.inject.Inject

class ValidateReviewUseCase @Inject constructor() {
    fun isValid(review: Review): Boolean {
        return !(review.body.isNullOrEmpty() || review.date.isNullOrEmpty() || review.name.isNullOrEmpty() || review.rating == null)
    }
}