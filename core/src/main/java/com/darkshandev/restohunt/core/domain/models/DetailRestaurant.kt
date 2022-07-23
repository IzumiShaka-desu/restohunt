package com.darkshandev.restohunt.core.domain.models

data class DetailRestaurant(
    val address: String,
    val categories: List<Category>,
    val city: String,
    val customerReviews: List<CustomerReview>,
    val description: String,
    val id: String,
    val menus: Menus,
    val name: String,
    val pictureId: String,
    val rating: Double
)