package com.micahnyabuto.coinsphere.di

import com.micahnyabuto.coinsphere.data.remote.CoinsApiService
import com.micahnyabuto.coinsphere.data.repository.CoinsRepositoryImpl
import com.micahnyabuto.coinsphere.domain.repository.CoinsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(Android) {
            install(Logging)
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                url("https://api.coingecko.com/api/v3/")
            }
        }
    }
    single { CoinsApiService(get()) }
    single<CoinsRepository> { CoinsRepositoryImpl(get()) }
}