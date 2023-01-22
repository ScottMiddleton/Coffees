package com.middleton.hotcoffees.coffee_review.data.repository

import com.middleton.hotcoffees.coffee_review.data.remote.ReviewApi
import com.middleton.hotcoffees.coffee_review.domain.mappers.toDto
import com.middleton.hotcoffees.coffee_review.domain.model.Review
import com.middleton.hotcoffees.coffee_review.domain.repository.CoffeeReviewRepository
import javax.inject.Inject

class CoffeeReviewRepositoryImpl @Inject constructor(
    private val api: ReviewApi,
) : CoffeeReviewRepository {

    override suspend fun submitReview(review: Review): Result<Unit> {
        return try {
            val response = api.submitReview(review.coffeeId, review.toDto())
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Error(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(Error(e.message ?: "Unknown error"))
        }
    }
}