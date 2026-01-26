package com.micahnyabuto.coinsphere.data.repository

import com.micahnyabuto.coinsphere.data.mapper.toDomain
import com.micahnyabuto.coinsphere.data.remote.CoinsApiService
import com.micahnyabuto.coinsphere.domain.model.Coin
import com.micahnyabuto.coinsphere.domain.repository.CoinsRepository

class CoinsRepositoryImpl(
    private val coinsApiService: CoinsApiService
): CoinsRepository {

    override suspend fun getCoins(): List<Coin>{
        val response = coinsApiService.getCoins()
        return response.map {it.toDomain()}

    }
}