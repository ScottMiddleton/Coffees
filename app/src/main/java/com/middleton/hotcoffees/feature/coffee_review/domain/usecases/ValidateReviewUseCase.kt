package com.middleton.hotcoffees.feature.coffee_review.domain.usecases

import com.middleton.hotcoffees.feature.coffee_review.domain.model.Review
import javax.inject.Inject

class ValidateReviewUseCase @Inject constructor() {
    fun isValid(review: Review): Boolean {
        return !(review.body.isNullOrEmpty() || review.name.isNullOrEmpty() || review.rating == null)
    }
}