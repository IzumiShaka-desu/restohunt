package com.darkshandev.restohunt.app.DI

import com.darkshandev.restohunt.core.domain.usecase.RestaurantInteractor
import com.darkshandev.restohunt.core.domain.usecase.RestaurantUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    fun provideRestaurantUseCase(useCase: RestaurantInteractor): RestaurantUseCase
}