package com.micahnyabuto.coinsphere.data.repository

import com.micahnyabuto.coinsphere.data.remote.Coin
import com.micahnyabuto.coinsphere.data.remote.CoinSphereApiService

class CoinSphereRepositoryImpl(
    private val coinSphereApiService:CoinSphereApiService
) : CoinSphereRepository{

    override suspend fun getCoins(): List<Coin>{
        return coinSphereApiService.getCoins()

    }
}