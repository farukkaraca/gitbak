package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.domain.repository.UserRepository
import javax.inject.Inject

class UnFollowUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(username: String): ApiResponse<Boolean> {
        return userRepository.unFollowUser(username)
    }
}