package com.darkshandev.restohunt.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darkshandev.restohunt.core.data.local.dao.RestaurantDao
import com.darkshandev.restohunt.core.data.local.entities.FavRestaurantEntity


@Database(
    entities = [FavRestaurantEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantFavDao(): RestaurantDao
}