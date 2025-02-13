package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.UserSearchResponse
import com.farukkaraca.gitbak.domain.repository.UserRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(query: String): ApiResponse<UserSearchResponse> {
        return userRepository.searchUsers(query)
    }
}