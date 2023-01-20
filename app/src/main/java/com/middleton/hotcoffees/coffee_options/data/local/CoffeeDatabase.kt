package com.middleton.hotcoffees.coffee_options.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoffeeEntity::class, CoffeeUserInteraction::class], version = 1)
abstract class CoffeeDatabase : RoomDatabase() {
    abstract fun coffeeDao(): CoffeeDao
}