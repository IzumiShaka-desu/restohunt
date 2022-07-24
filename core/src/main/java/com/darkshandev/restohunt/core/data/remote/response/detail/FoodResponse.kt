package com.darkshandev.restohunt.core.data.remote.response.detail

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("name")
    val name: String
)