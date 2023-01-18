package com.middleton.hotcoffees.coffee_options.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffees")
    suspend fun getAllCoffees(): List<CoffeeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coffees: List<CoffeeEntity>)

    @Query("SELECT * FROM coffees WHERE id = :id")
    fun getCoffeeById(id: Int): CoffeeEntity
}