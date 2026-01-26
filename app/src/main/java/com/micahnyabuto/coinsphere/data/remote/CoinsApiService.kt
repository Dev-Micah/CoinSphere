package com.micahnyabuto.coinsphere.data.remote

import com.micahnyabuto.coinsphere.data.remote.dtos.CoinDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.JsonConvertException

class CoinsApiService(private val client: HttpClient) {

    suspend fun getCoins(
        currency: String = "usd",
        order: String = "market_cap_desc",
        perPage: Int = 100,
        page: Int = 1
    ): List<CoinDto> {
        return try {
            client.get("coins/markets") {
                parameter("vs_currency", currency)
                parameter("order", order)
                parameter("per_page", perPage)
                parameter("page", page)
            }.body()
        } catch (e: JsonConvertException) {
            println("Error parsing JSON: ${e.localizedMessage}")
            emptyList()
        }
    }
}