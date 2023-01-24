package com.middleton.hotcoffees.di

import android.app.Application
import androidx.room.Room
import com.middleton.hotcoffees.coffee_options.data.local.CoffeeDatabase
import com.middleton.hotcoffees.coffee_options.data.remote.CoffeeApi
import com.middleton.hotcoffees.coffee_options.data.repository.CoffeeOptionsOptionsRepositoryImpl
import com.middleton.hotcoffees.coffee_options.domain.repository.CoffeeOptionsRepository
import com.middleton.hotcoffees.coffee_review.data.remote.ReviewApi
import com.middleton.hotcoffees.coffee_review.data.repository.CoffeeReviewRepositoryImpl
import com.middleton.hotcoffees.coffee_review.domain.repository.CoffeeReviewRepository
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
    fun provideCoffeeApi(client: OkHttpClient): CoffeeApi {
        return Retrofit.Builder()
            .baseUrl(CoffeeApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create()
    }
    @Provides
    @Singleton
    fun provideCoffeeDatabase(app: Application): CoffeeDatabase {
        return Room.databaseBuilder(app, CoffeeDatabase::class.java, "coffee.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideCoffeeRepository(
        api: CoffeeApi,
        db: CoffeeDatabase
    ): CoffeeOptionsRepository {
        return CoffeeOptionsOptionsRepositoryImpl(
            api = api,
            dao = db.coffeeDao()
        )
    }

    @Provides
    @Singleton
    fun provideReviewApi(client: OkHttpClient): ReviewApi {
        return Retrofit.Builder()
            .baseUrl(ReviewApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create()
    }

    @Provides
    @Singleton
    fun provideCoffeeReviewRepository(
        api: ReviewApi
    ): CoffeeReviewRepository {
        return CoffeeReviewRepositoryImpl(
            api = api
        )
    }
}