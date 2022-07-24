package com.darkshandev.restohunt.core.data.remote.response.detail

import com.google.gson.annotations.SerializedName


data class DetailResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("restaurant")
    val restaurant: DetailRestaurantResponse
)
