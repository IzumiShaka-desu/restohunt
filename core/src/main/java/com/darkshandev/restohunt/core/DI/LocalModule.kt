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
import net.sqlcipher.database.SQLiteDatabase.getBytes
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        val passphrase: ByteArray = getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "resto.db"
            ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun restauranFavDao(appDatabase: AppDatabase): RestaurantDao =
        appDatabase.restaurantFavDao()
}