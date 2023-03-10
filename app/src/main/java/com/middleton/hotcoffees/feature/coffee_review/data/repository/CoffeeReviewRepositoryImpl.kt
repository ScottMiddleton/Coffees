package com.middleton.hotcoffees.feature.coffee_review.data.repository

import com.middleton.hotcoffees.feature.coffee_review.data.remote.ReviewApi
import com.middleton.hotcoffees.feature.coffee_review.domain.mappers.toDto
import com.middleton.hotcoffees.feature.coffee_review.domain.model.Review
import com.middleton.hotcoffees.feature.coffee_review.domain.repository.CoffeeReviewRepository
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
                Result.failure(Error())
            }
        } catch (e: Exception) {
            Result.failure(Error())
        }
    }
}