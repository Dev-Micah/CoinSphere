package com.micahnyabuto.coinsphere.data.mapper

import com.micahnyabuto.coinsphere.data.remote.dtos.CoinDto
import com.micahnyabuto.coinsphere.domain.model.Coin

fun CoinDto.toDomain() : Coin{
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        image = image,
        currentPrice = currentPrice ?: 0.0,
        marketCap = marketCap ?: 0,
        marketCapRank = marketCapRank ?: 0,
        marketCapChange24h = marketCapChange24h ?: 0.0,
        priceChange24h = priceChange24h ?: 0.0,
        priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
        high24h = high24h ?: 0.0,
        low24h = low24h ?: 0.0,
        ath = ath ?: 0.0,
    )
}