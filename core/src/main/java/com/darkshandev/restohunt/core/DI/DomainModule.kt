package com.darkshandev.restohunt.core.DI

import com.darkshandev.restohunt.core.data.RestaurantRepository
import com.darkshandev.restohunt.core.domain.repositories.IRestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DomainModule {
    @Binds
    @Singleton
    fun provideRestaurantRepository(repository: RestaurantRepository): IRestaurantRepository
}