package com.darkshandev.restohunt.core.data.remote.response.detail

data class DetailRestaurantResponse(
    val address: String,
    val categories: List<CategoryResponse>,
    val city: String,
    val customerReviews: List<CustomerReviewResponse>,
    val description: String,
    val id: String,
    val menus: MenusResponse,
    val name: String,
    val pictureId: String,
    val rating: Double
)