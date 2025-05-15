package com.micahnyabuto.coinsphere.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val current_price: Double,
    val market_cap: Long,
    val market_cap_rank: Int,
    val market_cap_change_24h: Double,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
    val high_24h:Double,
    val low_24h:Double,
    val ath:Double,
)
