package com.darkshandev.restohunt.core.data.remote.response.detail

data class AddReviewResponse(
    val customerReviews: List<CustomerReviewResponse>,
    val error: Boolean,
    val message: String
)