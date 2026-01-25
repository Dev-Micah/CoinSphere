package com.micahnyabuto.coinsphere.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,

    @SerialName("current_price")
    val currentPrice: Double,

    @SerialName("market_cap")
    val marketCap: Long,

    @SerialName("market_cap_rank")
    val marketCapRank: Int,

    @SerialName("market_cap_change_24h")
    val marketCapChange24h: Double,

    @SerialName("price_change_24h")
    val priceChange24h: Double,

    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,

    @SerialName("high_24h")
    val high24h: Double,

    @SerialName("low_24h")
    val low24h: Double,

    @SerialName("ath")
    val ath: Double
)