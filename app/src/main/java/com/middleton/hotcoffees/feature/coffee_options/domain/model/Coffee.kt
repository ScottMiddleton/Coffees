package com.middleton.hotcoffees.feature.coffee_options.domain.model

data class Coffee(
    val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val imageUrl: String,
    val liked: Boolean
)
