package com.farukkaraca.gitbak.data.repository

import com.farukkaraca.gitbak.data.common.toResult
import com.farukkaraca.gitbak.data.model.AccessToken
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.remote.service.OAuthApiService
import com.farukkaraca.gitbak.di.CLIENT_ID
import com.farukkaraca.gitbak.di.CLIENT_SECRET
import com.farukkaraca.gitbak.domain.repository.GitHubAuthRepository
import javax.inject.Inject
import javax.inject.Named

class GitHubAuthRepositoryImpl @Inject constructor(
    private val oAuthApiService: OAuthApiService,
    @Named(CLIENT_ID) private val clientId: String,
    @Named(CLIENT_SECRET) private val clientSecret: String
) : GitHubAuthRepository {

    override suspend fun fetchAccessToken(authCode: String): ApiResponse<AccessToken> {
        try {
            val response = oAuthApiService.exchangeCodeForToken(
                clientId = clientId,
                clientSecret = clientSecret,
                code = authCode
            )

            return response.toResult()

        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }
}
