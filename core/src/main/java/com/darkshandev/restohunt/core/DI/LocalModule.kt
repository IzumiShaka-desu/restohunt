package com.darkshandev.restohunt.core.DI

import android.content.Context
import androidx.room.Room
import com.darkshandev.restohunt.core.data.local.dao.RestaurantDao
import com.darkshandev.restohunt.core.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase = Room
        .databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "resto.db"
        ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun restauranFavDao(appDatabase: AppDatabase): RestaurantDao =
        appDatabase.restaurantFavDao()
}