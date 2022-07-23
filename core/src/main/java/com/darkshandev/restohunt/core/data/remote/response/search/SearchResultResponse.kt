package com.darkshandev.restohunt.core.data.remote.response.search

import com.darkshandev.restohunt.core.data.remote.response.restaurant.RestaurantResponse

data class SearchResultResponse(
    val error: Boolean,
    val founded: Int,
    val restaurants: List<RestaurantResponse>
)