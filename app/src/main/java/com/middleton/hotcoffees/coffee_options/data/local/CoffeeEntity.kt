package com.middleton.hotcoffees.coffee_options.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "coffees")
@TypeConverters(IngredientsConverter::class)
data class CoffeeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val imageUrl: String,
    val liked: Boolean
)