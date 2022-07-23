package com.darkshandev.restohunt.core.data.remote.response.restaurant

data class RestaurantListResponse(
    val count: Int,
    val error: Boolean,
    val message: String,
    val restaurants: List<RestaurantResponse>
)