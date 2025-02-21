package com.farukkaraca.gitbak.data.repository


import android.content.SharedPreferences
import com.farukkaraca.gitbak.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : TokenRepository {

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
    }

    override fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    override fun clearToken() {
        sharedPreferences.edit().remove(ACCESS_TOKEN_KEY).apply()
    }

    override suspend fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }
}