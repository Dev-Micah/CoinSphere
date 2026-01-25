package com.micahnyabuto.coinsphere.di

import com.micahnyabuto.coinsphere.data.remote.CoinsApiService
import com.micahnyabuto.coinsphere.domain.repository.CoinsRepository
import com.micahnyabuto.coinsphere.data.repository.CoinsRepositoryImpl
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
    fun provideCoinSphereApiService(retrofit: Retrofit) : CoinsApiService{
        return retrofit.create(CoinsApiService::class.java)

    }
    @Singleton
    @Provides
    fun provideRepository(coinSphereApiService: CoinsApiService): CoinsRepository {
        return CoinsRepositoryImpl(coinSphereApiService)
    }

}