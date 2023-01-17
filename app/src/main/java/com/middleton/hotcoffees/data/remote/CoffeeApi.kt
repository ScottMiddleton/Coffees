package com.middleton.hotcoffees.data.remote

import com.middleton.hotcoffees.data.remote.dto.CoffeeDto
import retrofit2.http.GET

interface CoffeeApi {
    @GET("coffee/hot")
    suspend fun getCoffees(): List<CoffeeDto>

    companion object {
        const val BASE_URL = "https://api.sampleapis.com/"
    }
}