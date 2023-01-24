package com.middleton.hotcoffees.feature.coffee_review.domain.mappers

import com.middleton.hotcoffees.feature.coffee_review.data.remote.dto.ReviewDto
import com.middleton.hotcoffees.feature.coffee_review.domain.model.Review

fun Review.toDto(): ReviewDto {
    return ReviewDto(name = this.name!!, body = this.body!!, rating = this.rating!!)
}