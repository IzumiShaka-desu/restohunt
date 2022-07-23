package com.darkshandev.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkshandev.restohunt.core.domain.models.Restaurant
import com.darkshandev.restohunt.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoriteViewmodel(private val useCase: RestaurantUseCase) :
    ViewModel() {
    val favRestaurant: StateFlow<List<Restaurant>> = useCase.getFavRestaurants()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}