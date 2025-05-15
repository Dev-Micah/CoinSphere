package com.micahnyabuto.coinsphere.di

import com.micahnyabuto.coinsphere.data.remote.CoinSphereApiService
import com.micahnyabuto.coinsphere.data.repository.CoinSphereRepository
import com.micahnyabuto.coinsphere.data.repository.CoinSphereRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideCoinSphereApiService(retrofit: Retrofit) : CoinSphereApiService{
        return retrofit.create(CoinSphereApiService::class.java)

    }
    @Singleton
    @Provides
    fun provideRepository(coinSphereApiService: CoinSphereApiService): CoinSphereRepository {
        return CoinSphereRepositoryImpl(coinSphereApiService)
    }

}