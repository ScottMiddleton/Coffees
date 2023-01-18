package com.middleton.hotcoffees.coffee_options.data.remote.dto

data class CoffeeDto (
    val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val image: String
)