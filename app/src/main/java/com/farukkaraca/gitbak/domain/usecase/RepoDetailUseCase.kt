package com.farukkaraca.gitbak.domain.usecase

import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.RepoDetail
import com.farukkaraca.gitbak.domain.repository.UserRepository
import javax.inject.Inject

class RepoDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(username: String, repo: String): ApiResponse<RepoDetail> {
        return userRepository.getRepoDetail(username, repo)
    }
}