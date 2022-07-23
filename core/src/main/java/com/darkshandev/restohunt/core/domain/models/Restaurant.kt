package com.darkshandev.restohunt.core.domain.models

data class Restaurant(
    val city: String,
    val description: String,
    val id: String,
    val name: String,
    val pictureId: String,
    val rating: Double
)