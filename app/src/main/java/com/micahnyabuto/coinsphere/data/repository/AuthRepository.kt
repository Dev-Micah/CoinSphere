package com.micahnyabuto.coinsphere.data.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, name: String, password: String): Result<Unit>
}
