package com.middleton.hotcoffees.feature.coffee_options.domain.mappers

import com.middleton.hotcoffees.feature.coffee_options.data.local.CoffeeAndUserInteraction
import com.middleton.hotcoffees.feature.coffee_options.data.local.CoffeeEntity
import com.middleton.hotcoffees.feature.coffee_options.data.remote.dto.CoffeeDto
import com.middleton.hotcoffees.feature.coffee_options.domain.model.Coffee

fun CoffeeDto.toCoffeeEntity() : CoffeeEntity {
    return CoffeeEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        ingredients = this.ingredients,
        imageUrl = this.image
    )
}

fun CoffeeAndUserInteraction.toCoffee() : Coffee {
    return Coffee(
        id = this.coffee.id,
        title = this.coffee.title,
        description = this.coffee.description,
        ingredients = this.coffee.ingredients,
        imageUrl = this.coffee.imageUrl,
        liked = this.userInteraction?.isLiked ?: false
    )
}