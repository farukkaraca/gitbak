package com.farukkaraca.gitbak.data.repository

import com.farukkaraca.gitbak.data.common.toResult
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.GithubRepo
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.data.model.UserSearchResponse
import com.farukkaraca.gitbak.data.remote.service.GitHubApiService
import com.farukkaraca.gitbak.data.remote.service.GitHubAuthenticatedApiService
import com.farukkaraca.gitbak.data.session.SessionManager
import com.farukkaraca.gitbak.domain.repository.UserRepository
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService,
    private val authenticatedApiService: GitHubAuthenticatedApiService,
    private val sessionManager: SessionManager,
) : UserRepository {

    override suspend fun searchUsers(query: String): ApiResponse<UserSearchResponse> {
        try {
            if (sessionManager.checkIsLoggedIn()) {
                val response = authenticatedApiService.searchUsers(query)
                return response.toResult()
            } else {
                val response = apiService.searchUsers(query)
                return response.toResult()
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }

    override suspend fun getUserDetail(username: String): ApiResponse<UserDetail> {
        try {
            if (sessionManager.checkIsLoggedIn()) {
                val response = authenticatedApiService.getUserDetail(username)
                return response.toResult()
            } else {
                val response = apiService.getUserDetail(username)
                return response.toResult()
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }

    override suspend fun getUserRepositories(
        username: String,
        page: Int,
        perPage: Int
    ): ApiResponse<List<GithubRepo>> {
        try {
            if (sessionManager.checkIsLoggedIn()) {
                val response = authenticatedApiService.getUserRepositories(username, page, perPage)
                return response.toResult()
            } else {
                val response = apiService.getUserRepositories(username, page, perPage)
                return response.toResult()
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }
}