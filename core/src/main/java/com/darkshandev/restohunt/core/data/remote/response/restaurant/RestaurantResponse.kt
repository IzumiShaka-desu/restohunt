package com.darkshandev.restohunt.core.data.remote.response.restaurant

data class RestaurantResponse(
    val city: String,
    val description: String,
    val id: String,
    val name: String,
    val pictureId: String,
    val rating: Double
)