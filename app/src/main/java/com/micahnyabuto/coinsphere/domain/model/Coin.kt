package com.micahnyabuto.coinsphere.domain.model


data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Long,
    val marketCapRank: Int,
    val marketCapChange24h: Double,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val high24h: Double,
    val low24h: Double,
    val ath: Double
)