package com.darkshandev.restohunt.core.domain.usecase

import com.darkshandev.restohunt.core.domain.models.AppState
import com.darkshandev.restohunt.core.domain.models.CustomerReview
import com.darkshandev.restohunt.core.domain.models.DetailRestaurant
import com.darkshandev.restohunt.core.domain.models.Restaurant
import com.darkshandev.restohunt.core.domain.repositories.IRestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RestaurantInteractor @Inject constructor(
    private val restaurantRepository: IRestaurantRepository
) : RestaurantUseCase {
    override fun getRestaurants(): Flow<AppState<List<Restaurant>>> =
        restaurantRepository.getRestaurants()

    override fun getRestaurantDetailBy(id: String): Flow<AppState<DetailRestaurant>> =
        restaurantRepository.getRestaurantDetailBy(id)

    override fun updateSearchQuery(query: String) {
        restaurantRepository.updateSearchQuery(query)
    }

    override fun searchRestaurantResult(): Flow<AppState<List<Restaurant>>> =
        restaurantRepository.searchRestaurantResult()


    override fun addReview(
        name: String,
        review: String,
        id: String
    ): Flow<AppState<List<CustomerReview>>> = restaurantRepository.addReview(name, review, id)

    override suspend fun addFavRestaurant(restaurant: Restaurant) =
        restaurantRepository.addFavRestaurant(restaurant)

    override suspend fun removeFavRestaurant(restaurant: Restaurant) =
        restaurantRepository.removeFavRestaurant(restaurant)

    override fun isFavoriteRestaurant(id: String): Flow<Boolean> =
        restaurantRepository.isFavoriteRestaurant(id)

    override fun getFavRestaurants(): Flow<List<Restaurant>> =
        restaurantRepository.getFavRestaurants()
}
