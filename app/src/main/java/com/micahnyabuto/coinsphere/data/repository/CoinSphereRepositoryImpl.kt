package com.micahnyabuto.coinsphere.data.repository

import com.micahnyabuto.coinsphere.data.remote.Coin
import com.micahnyabuto.coinsphere.data.remote.CoinSphereApiService
import javax.inject.Inject

class CoinSphereRepository(
    private val coinSphereApiService:CoinSphereApiService
) {

    override suspend fun getCoins(): List<Coin>{
        return coinSphereApiService.getCoins()

    }
}