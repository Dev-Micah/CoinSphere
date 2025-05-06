package com.micahnyabuto.coinsphere.data.repository

import com.micahnyabuto.coinsphere.data.remote.Coin

interface CoinSphereRepository {
    suspend fun getCoins(): List<Coin>
}