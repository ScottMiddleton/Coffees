package com.middleton.hotcoffees.feature.coffee_review.domain.repository

import com.middleton.hotcoffees.feature.coffee_review.domain.model.Review

interface CoffeeReviewRepository {
    suspend fun submitReview(review: Review): Result<Unit>
}