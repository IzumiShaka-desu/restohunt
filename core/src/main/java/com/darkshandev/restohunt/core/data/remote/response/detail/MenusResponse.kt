package com.darkshandev.restohunt.core.data.remote.response.detail

import com.google.gson.annotations.SerializedName

data class MenusResponse(
    @SerializedName("drinks")
    val drinks: List<DrinkResponse>,
    @SerializedName("foods")
    val foods: List<FoodResponse>
)