package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.domain.repository.TokenRepository
import javax.inject.Inject

class SaveAccessTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    fun execute(accessToken: String) {
        tokenRepository.saveAccessToken(accessToken)
    }
}