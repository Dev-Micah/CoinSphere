package com.micahnyabuto.coinsphere.data.remote

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val current_price: Double,
    val market_cap: Double,
    val market_cap_rank: Double,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
    val high_24h:Double,
    val low_24h:Double
)
