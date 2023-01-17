package com.middleton.hotcoffees.domain.mappers

import com.middleton.hotcoffees.data.remote.dto.CoffeeDto
import com.middleton.hotcoffees.domain.model.Coffee

fun CoffeeDto.toCoffee() : Coffee {
    return Coffee(
        id = this.id,
        title = this.title,
        description = this.description,
        ingredients = this.ingredients,
        imageUrl = this.image,
        liked = false
    )
}