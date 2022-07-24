package com.darkshandev.favorites.DI

import android.content.Context
import com.darkshandev.favorites.FavoriteFragment
import com.darkshandev.restohunt.app.DI.FavoritesModuleDependecies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoritesModuleDependecies::class])
interface FavoritesComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependency: FavoritesModuleDependecies): Builder
        fun build(): FavoritesComponent
    }
}