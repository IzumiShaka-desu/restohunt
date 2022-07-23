package com.darkshandev.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darkshandev.restohunt.core.domain.usecase.RestaurantUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val useCase: RestaurantUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewmodel::class.java) -> {
                FavoriteViewmodel(useCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
        }
}