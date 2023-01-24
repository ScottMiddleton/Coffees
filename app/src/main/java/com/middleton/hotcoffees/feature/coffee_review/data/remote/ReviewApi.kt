package com.middleton.hotcoffees.feature.coffee_review.data.remote

import com.middleton.hotcoffees.feature.coffee_review.data.remote.dto.ReviewDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApi {
    @POST("coffee/{coffeeId}/review")
    suspend fun submitReview(@Path("coffeeId") coffeeId: Int, @Body review: ReviewDto): Response<Void>

    companion object {
        const val BASE_URL = "https://api.sampleapis.com/"
    }
}