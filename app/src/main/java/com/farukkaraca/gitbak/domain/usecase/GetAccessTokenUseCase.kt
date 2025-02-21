package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.domain.repository.TokenRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
     suspend fun execute(): String? {
        return tokenRepository.getAccessToken()
    }
}