package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.domain.repository.UserRepository
import javax.inject.Inject

class UserFollowersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(username: String, page: Int, perPage: Int): ApiResponse<List<User>> {
        return userRepository.getUserFollowers(username, page, perPage)
    }
}