package com.farukkaraca.gitbak.domain.usecase


import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(): ApiResponse<UserDetail> {
        return userRepository.getCurrentUser()
    }
}
