package com.micahnyabuto.coinsphere.data.remote

import com.micahnyabuto.coinsphere.data.remote.dtos.CoinDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsApiService {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1
    ): List<CoinDto>
}