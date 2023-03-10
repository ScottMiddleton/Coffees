package com.middleton.hotcoffees.feature.coffee_options.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffees")
    fun getAllCoffees(): Flow<List<CoffeeAndUserInteraction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coffees: List<CoffeeEntity>)

    @Query("SELECT * FROM coffees WHERE id = :id")
    fun getCoffeeById(id: Int): CoffeeAndUserInteraction

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateUserInteraction(interaction: CoffeeUserInteraction)
}
