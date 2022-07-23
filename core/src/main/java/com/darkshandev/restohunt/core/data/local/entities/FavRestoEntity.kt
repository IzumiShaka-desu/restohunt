package com.darkshandev.restohunt.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "restaurantsFav")
data class FavRestaurantEntity(
    val city: String,
    val description: String,
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    val pictureId: String,
    val rating: Double
)