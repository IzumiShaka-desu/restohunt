package com.darkshandev.restohunt.app.DI

import com.darkshandev.restohunt.core.domain.usecase.RestaurantUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoritesModuleDependecies {

    fun provideRestaurantUseCase(): RestaurantUseCase
}