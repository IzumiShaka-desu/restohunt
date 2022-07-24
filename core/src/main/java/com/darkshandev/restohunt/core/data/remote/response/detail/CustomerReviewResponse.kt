package com.darkshandev.restohunt.core.data.remote.response.detail

import com.google.gson.annotations.SerializedName

data class CustomerReviewResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("review")
    val review: String
)