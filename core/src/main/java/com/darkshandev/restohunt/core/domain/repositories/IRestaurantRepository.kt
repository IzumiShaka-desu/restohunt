package com.darkshandev.restohunt.core.domain.repositories

import com.darkshandev.restohunt.core.domain.models.AppState
import com.darkshandev.restohunt.core.domain.models.CustomerReview
import com.darkshandev.restohunt.core.domain.models.DetailRestaurant
import com.darkshandev.restohunt.core.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow

interface IRestaurantRepository {
    fun getRestaurants(): Flow<AppState<List<Restaurant>>>
    fun getRestaurantDetailBy(id: String): Flow<AppState<DetailRestaurant>>
    fun updateSearchQuery(query: String)
    fun searchRestaurantResult(): Flow<AppState<List<Restaurant>>>
    fun addReview(name: String, review: String, id: String): Flow<AppState<List<CustomerReview>>>
    suspend fun addFavRestaurant(restaurant: Restaurant)
    suspend fun removeFavRestaurant(restaurant: Restaurant)
    fun isFavoriteRestaurant(id: String): Flow<Boolean>
    fun getFavRestaurants(): Flow<List<Restaurant>>


}