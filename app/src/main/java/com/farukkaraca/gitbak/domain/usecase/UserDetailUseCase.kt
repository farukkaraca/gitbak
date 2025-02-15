package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.domain.repository.UserRepository
import javax.inject.Inject

class UserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(username: String): ApiResponse<UserDetail> {
        return userRepository.getUserDetail(username)
    }
}