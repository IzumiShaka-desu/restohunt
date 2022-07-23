package com.darkshandev.restohunt.core.data.remote.response.detail

data class MenusResponse(
    val drinks: List<DrinkResponse>,
    val foods: List<FoodResponse>
)