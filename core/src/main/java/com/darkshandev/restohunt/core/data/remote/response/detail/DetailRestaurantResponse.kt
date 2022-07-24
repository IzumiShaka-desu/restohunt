package com.darkshandev.restohunt.core.data.remote.response.detail

import com.google.gson.annotations.SerializedName


data class DetailRestaurantResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("categories")
    val categories: List<CategoryResponse>,
    @SerializedName("city")
    val city: String,
    @SerializedName("customerReviews")
    val customerReviews: List<CustomerReviewResponse>,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("menus")
    val menus: MenusResponse,
    @SerializedName("name")
    val name: String,
    @SerializedName("pictureId")
    val pictureId: String,
    @SerializedName("rating")
    val rating: Double
)