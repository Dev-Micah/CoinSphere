package com.micahnyabuto.coinsphere.data.repository

import com.micahnyabuto.coinsphere.data.remote.Coin
import com.micahnyabuto.coinsphere.data.remote.CoinSphereApiService
import javax.inject.Inject

class CoinSphereRepository @Inject constructor(
    private val coinSphereApiService:CoinSphereApiService
) {

    suspend fun getCoins(): List<Coin>{
        return coinSphereApiService.getCoins()

    }
}