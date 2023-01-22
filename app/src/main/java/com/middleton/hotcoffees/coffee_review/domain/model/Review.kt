package com.middleton.hotcoffees.coffee_review.domain.model

data class Review(
    val coffeeId: Int,
    val name: String? = null,
    val date: String? = null,
    val body: String? = null,
    val rating: Int? = null
)