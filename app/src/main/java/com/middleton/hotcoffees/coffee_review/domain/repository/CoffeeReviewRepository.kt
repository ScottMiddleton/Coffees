package com.middleton.hotcoffees.coffee_review.domain.repository

import com.middleton.hotcoffees.coffee_review.domain.model.Review

interface CoffeeReviewRepository {
    suspend fun submitReview(review: Review): Result<Unit>
}