package com.micahnyabuto.coinsphere.data.remote

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val current_price: Double,
    val price_change_percentage_24h: Double
)
