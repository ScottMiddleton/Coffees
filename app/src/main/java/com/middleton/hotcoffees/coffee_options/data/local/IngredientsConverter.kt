package com.middleton.hotcoffees.coffee_options.data.local

import androidx.room.TypeConverter

class IngredientsConverter {
    @TypeConverter
    fun fromIngredientsList(ingredients: List<String>): String {
        return ingredients.joinToString(",")
    }

    @TypeConverter
    fun toIngredientsList(ingredients: String): List<String> {
        return ingredients.split(",")
    }
}