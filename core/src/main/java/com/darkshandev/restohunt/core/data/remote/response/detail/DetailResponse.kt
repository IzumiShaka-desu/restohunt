package com.darkshandev.restohunt.core.data.remote.response.detail

data class DetailResponse(
    val error: Boolean,
    val message: String,
    val restaurant: DetailRestaurantResponse
)