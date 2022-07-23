package com.darkshandev.restohunt.core.DI

import com.darkshandev.restohunt.core.BuildConfig
import com.darkshandev.restohunt.core.data.remote.networks.RestaurantService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(
        interceptor: HttpLoggingInterceptor,

        ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()


    @Singleton
    @Provides
    fun provideRetrofitClient(
        client: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideRestoService(
        retrofit: Retrofit
    ): RestaurantService = retrofit
        .create(RestaurantService::class.java)


}
