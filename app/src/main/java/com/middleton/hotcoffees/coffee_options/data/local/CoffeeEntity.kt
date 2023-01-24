package com.middleton.hotcoffees.coffee_options.data.local

import androidx.room.*
import androidx.room.ForeignKey.Companion.NO_ACTION

@Entity(tableName = "coffees")
@TypeConverters(IngredientsConverter::class)
data class CoffeeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val imageUrl: String
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = CoffeeEntity::class,
        parentColumns = ["id"],
        childColumns = ["coffeeId"],
        onDelete = NO_ACTION
    )]
)
data class CoffeeUserInteraction(
    @PrimaryKey val coffeeId: Int,
    val isLiked: Boolean = false
)

data class CoffeeAndUserInteraction(
    @Embedded val coffee: CoffeeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "coffeeId"
    )
    val userInteraction: CoffeeUserInteraction?
)