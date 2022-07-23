package com.darkshandev.restohunt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkshandev.restohunt.core.domain.models.AppState
import com.darkshandev.restohunt.core.domain.models.Restaurant
import com.darkshandev.restohunt.core.domain.usecase.RestaurantUseCase
import com.darkshandev.restohunt.core.utils.asRestaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewmodel @Inject constructor(private val useCase: RestaurantUseCase) :
    ViewModel() {
    val restaurant: StateFlow<AppState<List<Restaurant>>> = useCase.getRestaurants().stateIn(
        viewModelScope,
        SharingStarted.Lazily, initialValue = AppState.Initial()
    )

    fun updateSearchQuery(query: String) {
        useCase.updateSearchQuery(query)
    }

    val restaurantSearchResult = useCase.searchRestaurantResult().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        initialValue = AppState.Initial()
    )

    private val _selectedId = MutableStateFlow("")
    fun setSelectedId(id: String) {
        _selectedId.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val detailRestaurant = _selectedId.distinctUntilChanged { old, new -> old == new }
        .transformLatest {
            if (it.isEmpty()) {
                emit(AppState.Initial())
            } else {
                useCase
                    .getRestaurantDetailBy(it)
                    .collect { result ->
                        this.emit(result)
                    }
            }


        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            AppState.Initial()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val isFavorite = _selectedId.distinctUntilChanged { old, new -> old == new }
        .transformLatest {
            if (it.isEmpty()) {
                emit(null)
            } else {
                useCase
                    .isFavoriteRestaurant(it)
                    .collect { result ->
                        this.emit(result)
                    }
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
        )

    fun toggleFavorite() {
        viewModelScope.launch {
            detailRestaurant.value.data?.let { detail ->
                isFavorite.value?.let {
                    if (it) {
                        useCase.removeFavRestaurant(detail.asRestaurant())
                    } else {
                        useCase.addFavRestaurant(detail.asRestaurant())
                    }
                }
                this.cancel()
            }

        }
    }
}