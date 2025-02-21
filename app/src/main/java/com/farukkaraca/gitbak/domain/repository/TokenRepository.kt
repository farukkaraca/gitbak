package com.farukkaraca.gitbak.domain.repository


interface TokenRepository {
    fun saveAccessToken(token: String)
    fun clearToken()
    suspend fun getAccessToken(): String?
}