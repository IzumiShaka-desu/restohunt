package com.darkshandev.restohunt.core.data

import android.util.Log
import com.darkshandev.restohunt.core.data.local.dao.RestaurantDao
import com.darkshandev.restohunt.core.data.remote.datasources.RestaurantDatasources
import com.darkshandev.restohunt.core.data.remote.request.AddReviewPost
import com.darkshandev.restohunt.core.domain.models.AppState
import com.darkshandev.restohunt.core.domain.models.CustomerReview
import com.darkshandev.restohunt.core.domain.models.DetailRestaurant
import com.darkshandev.restohunt.core.domain.models.Restaurant
import com.darkshandev.restohunt.core.domain.repositories.IRestaurantRepository
import com.darkshandev.restohunt.core.utils.asEntity
import com.darkshandev.restohunt.core.utils.asModel
import com.darkshandev.restohunt.core.utils.mapAsModel
import com.darkshandev.restohunt.core.utils.mapToModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val datasource: RestaurantDatasources,
    private val dao: RestaurantDao
) : IRestaurantRepository {
    override fun getRestaurants(): Flow<AppState<List<Restaurant>>> =
        flow<AppState<List<Restaurant>>> {
            emit(AppState.Loading())
            val result = datasource.getRestaurants()
            result.onFailure {
                emit(AppState.Error(it.message!!))
            }.onSuccess {
                if (it.error) {

                    emit(AppState.Error(it.message))
                } else {
                    emit(AppState.Success(it.restaurants.mapToModel()))

                }
            }
        }.flowOn(Dispatchers.IO)

    override fun getRestaurantDetailBy(id: String): Flow<AppState<DetailRestaurant>> =
        flow<AppState<DetailRestaurant>> {
            try {
                emit(AppState.Loading())
                val result = datasource.getDetailRestaurantBy(id)
                result.onFailure {
                    emit(AppState.Error(it.message!!))
                }.onSuccess {
                    if (it.error) {
                        emit(AppState.Error(it.message))
                    } else {
                        emit(AppState.Success(it.restaurant.asModel()))
                    }
                }
            } catch (e: Exception) {
                Log.e("fetch detail error:", e.message ?: e.toString())
                emit(AppState.Error("please try again"))
            }

        }.flowOn(Dispatchers.IO)

    private val _query = MutableStateFlow("")
    override fun updateSearchQuery(query: String) {
        _query.value = query
    }


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun searchRestaurantResult(): Flow<AppState<List<Restaurant>>> = _query.debounce(500)
        .flatMapLatest { query ->
            flow<AppState<List<Restaurant>>> {
                if (query == "") {
                    emit(AppState.Initial())
                } else {
                    val result = datasource.searchRestaurantBy(query)
                    result.onFailure {
                        emit(AppState.Error(it.message!!))
                    }.onSuccess {
                        if (it.error || it.founded < 1) {
                            emit(AppState.Error("No results found"))
                        } else {
                            emit(AppState.Success(it.restaurants.mapToModel()))
                        }
                    }
                }

            }.onStart {
                emit(AppState.Loading())
            }.catch {
                emit(
                    AppState.Error(
                        message = "Sorry, there are no restaurant by that keyword. Keep looking!",
                    )
                )
            }
        }
        .flowOn(Dispatchers.IO)


    override fun addReview(
        name: String,
        review: String,
        id: String
    ): Flow<AppState<List<CustomerReview>>> = flow<AppState<List<CustomerReview>>> {
        emit(AppState.Loading())
        val result = datasource.addNewReview(AddReviewPost(id, name, review))
        result.onFailure {
            emit(AppState.Error(it.message!!))
        }.onSuccess {
            if (it.error || it.customerReviews.isEmpty()) {
                emit(AppState.Error(it.message))
            } else {
                emit(AppState.Success(it.customerReviews.mapAsModel()))
            }
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun addFavRestaurant(restaurant: Restaurant) = withContext(Dispatchers.IO) {
        dao.addFav(restaurant = restaurant.asEntity())
    }

    override suspend fun removeFavRestaurant(restaurant: Restaurant) = withContext(Dispatchers.IO) {
        dao.deleteFav(restaurant = restaurant.asEntity())
    }

    override fun isFavoriteRestaurant(id: String): Flow<Boolean> =
        dao.isFavorite(id).map { it != null }.flowOn(Dispatchers.IO)

    override fun getFavRestaurants(): Flow<List<Restaurant>> =
        dao.getFavorites().map { favs -> favs.map { it.asModel() } }.flowOn(Dispatchers.IO)
}
