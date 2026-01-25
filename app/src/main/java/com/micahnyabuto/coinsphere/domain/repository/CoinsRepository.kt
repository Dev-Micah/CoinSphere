package com.micahnyabuto.coinsphere.domain.repository

import com.micahnyabuto.coinsphere.domain.model.Coin

interface CoinsRepository {
    suspend fun getCoins(): List<Coin>
}