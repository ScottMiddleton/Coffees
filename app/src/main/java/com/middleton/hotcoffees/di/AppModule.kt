package com.middleton.hotcoffees.di

import com.middleton.hotcoffees.coffee_options.data.remote.CoffeeApi
import com.middleton.hotcoffees.coffee_options.data.repository.CoffeeRepositoryImpl
import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun provideCoffeeRepository(
        api: CoffeeApi
    ): CoffeeRepository {
        return CoffeeRepositoryImpl(
            api = api
        )
    }
}