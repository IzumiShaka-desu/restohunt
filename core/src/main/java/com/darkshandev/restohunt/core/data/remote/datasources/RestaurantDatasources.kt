package com.darkshandev.restohunt.core.data.remote.datasources

import android.content.Context
import com.darkshandev.restohunt.core.R
import com.darkshandev.restohunt.core.data.remote.networks.RestaurantService
import com.darkshandev.restohunt.core.data.remote.request.AddReviewPost
import com.darkshandev.restohunt.core.data.remote.response.detail.AddReviewResponse
import com.darkshandev.restohunt.core.data.remote.response.detail.DetailResponse
import com.darkshandev.restohunt.core.data.remote.response.restaurant.RestaurantListResponse
import com.darkshandev.restohunt.core.data.remote.response.search.SearchResultResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class RestaurantDatasources @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: RestaurantService
) {

    suspend fun getRestaurants(): Result<RestaurantListResponse> =
        getResponse(context.getString(R.string.cannot_get_restaurants)) {
            service.getRestaurants()
        }

    suspend fun getDetailRestaurantBy(id: String): Result<DetailResponse> =
        getResponse(context.getString(R.string.cannot_get_restaurants_detail)) {
            service.getDetailRestaurant(id)
        }

    suspend fun searchRestaurantBy(query: String): Result<SearchResultResponse> =
        getResponse(context.getString(R.string.cannot_search_restaurant)) {
            service.getSearchRestaurants(query)
        }

    suspend fun addNewReview(review: AddReviewPost): Result<AddReviewResponse> =
        getResponse(context.getString(R.string.cannot_get_restaurants)) {
            service.postReview(review)
        }

    private suspend fun <T> getResponse(
        defaultErrorMessage: String,
        request: suspend () -> Response<T>

    ): Result<T> {
        return try {

            val result = request.invoke()
            if (result.isSuccessful) {
                val body = result.body()
                body?.let {
                    return Result.success(it)
                } ?: run {
                    return Result.failure(Exception("${result.code()} ${result.message()}"))
                }
            } else {
                Result.failure(Exception("${result.code()} ${result.message()}"))
            }
        } catch (e: Throwable) {
            Result.failure(Exception(defaultErrorMessage))
        }
    }
}
