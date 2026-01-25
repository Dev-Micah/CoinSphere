package com.micahnyabuto.coinsphere.data.mapper

import com.micahnyabuto.coinsphere.data.remote.dtos.CoinDto
import com.micahnyabuto.coinsphere.domain.model.Coin

fun CoinDto.toDomain() : Coin{
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        image = image,
        currentPrice = currentPrice,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
        marketCapChange24h = marketCapChange24h,
        priceChange24h = priceChange24h,
        priceChangePercentage24h = priceChangePercentage24h,
        high24h = high24h,
        low24h = low24h,
        ath = ath,
    )
}