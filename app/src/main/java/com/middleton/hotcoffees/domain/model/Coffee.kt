package com.middleton.hotcoffees.domain.model

data class Coffee(
    val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val imageUrl: String,
    val liked: Boolean
)
