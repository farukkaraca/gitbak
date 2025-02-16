package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.data.model.AccessToken
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.domain.repository.GitHubAuthRepository
import javax.inject.Inject

class GithubAuthUseCase @Inject constructor(
    private val gitHubAuthRepository: GitHubAuthRepository
) {
    suspend fun execute(authCode: String): ApiResponse<AccessToken> {
        return gitHubAuthRepository.fetchAccessToken(authCode)
    }
}