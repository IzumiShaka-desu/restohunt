package com.darkshandev.restohunt.core.data.remote.networks

import com.darkshandev.restohunt.core.data.remote.request.AddReviewPost
import com.darkshandev.restohunt.core.data.remote.response.detail.AddReviewResponse
import com.darkshandev.restohunt.core.data.remote.response.detail.DetailResponse
import com.darkshandev.restohunt.core.data.remote.response.restaurant.RestaurantListResponse
import com.darkshandev.restohunt.core.data.remote.response.search.SearchResultResponse
import retrofit2.Response
import retrofit2.http.*

interface RestaurantService {
    @GET("/list")
    suspend fun getRestaurants(): Response<RestaurantListResponse>

    @GET("/detail/{id}")
    suspend fun getDetailRestaurant(
        @Path("id") id: String
    ): Response<DetailResponse>

    @GET("/search")
    suspend fun getSearchRestaurants(@Query("q") query: String): Response<SearchResultResponse>

    @POST("/review")
    suspend fun postReview(
        @Body review: AddReviewPost
    ): Response<AddReviewResponse>
}