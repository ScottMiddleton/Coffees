package com.middleton.hotcoffees.feature.coffee_options.data.remote

import com.middleton.hotcoffees.feature.coffee_options.data.remote.dto.CoffeeDto
import retrofit2.http.GET

interface CoffeeApi {
    @GET("coffee/hot")
    suspend fun getCoffees(): List<CoffeeDto>

    companion object {
        const val BASE_URL = "https://api.sampleapis.com/"
    }
}