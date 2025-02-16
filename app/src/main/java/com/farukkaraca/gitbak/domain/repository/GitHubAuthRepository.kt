package com.farukkaraca.gitbak.domain.repository

import com.farukkaraca.gitbak.data.model.AccessToken
import com.farukkaraca.gitbak.data.model.ApiResponse

interface GitHubAuthRepository {
    suspend fun fetchAccessToken(authCode: String): ApiResponse<AccessToken>
}
