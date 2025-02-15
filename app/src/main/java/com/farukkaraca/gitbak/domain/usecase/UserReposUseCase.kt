package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.GithubRepo
import com.farukkaraca.gitbak.domain.repository.UserRepository
import javax.inject.Inject

class UserReposUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(username: String, page: Int, perPage: Int): ApiResponse<List<GithubRepo>> {
        return userRepository.getUserRepositories(username, page, perPage)
    }
}