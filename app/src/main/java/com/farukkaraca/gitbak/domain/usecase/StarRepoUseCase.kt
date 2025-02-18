package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.domain.repository.UserRepository
import javax.inject.Inject

class StarRepoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(username: String, repo: String): ApiResponse<Boolean> {
        return userRepository.starRepo(username, repo)
    }
}