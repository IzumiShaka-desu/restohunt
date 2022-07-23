package com.darkshandev.restohunt.core.data.local.dao

import androidx.room.*
import com.darkshandev.restohunt.core.data.local.entities.FavRestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurantsFav")
    fun getFavorites(): Flow<List<FavRestaurantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFav(restaurant: FavRestaurantEntity)

    @Delete
    suspend fun deleteFav(restaurant: FavRestaurantEntity)

    @Query("SELECT * FROM restaurantsFav WHERE id = :id")
    fun isFavorite(id: String): Flow<FavRestaurantEntity?>
}