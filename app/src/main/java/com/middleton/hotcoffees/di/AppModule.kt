package com.middleton.hotcoffees.di

import com.middleton.hotcoffees.data.remote.CoffeeApi
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): CoffeeApi {
        return Retrofit.Builder()
            .baseUrl(CoffeeApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create()
    }
}