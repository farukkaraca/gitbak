package com.farukkaraca.gitbak.data.repository

import com.farukkaraca.gitbak.data.common.toResult
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.GithubRepo
import com.farukkaraca.gitbak.data.model.RepoDetail
import com.farukkaraca.gitbak.data.model.User
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

    override suspend fun getCurrentUser(): ApiResponse<UserDetail> {
        try {
            if (sessionManager.checkIsLoggedIn()) {
                val response = authenticatedApiService.getCurrentUser()
                return response.toResult()
            } else {
                return ApiResponse.Error(Exception("auth required"))
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }

    override suspend fun getUserFollowers(
        username: String,
        page: Int,
        perPage: Int
    ): ApiResponse<List<User>> {
        try {
            if (sessionManager.checkIsLoggedIn()) {
                val response = authenticatedApiService.getUserFollowers(
                    username,
                    page,
                    perPage
                )
                return response.toResult()
            } else {
                val response = apiService.getUserFollowers(
                    username,
                    page,
                    perPage
                )
                return response.toResult()
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }

    override suspend fun getUserFollowing(
        username: String,
        page: Int,
        perPage: Int
    ): ApiResponse<List<User>> {
        try {
            if (sessionManager.checkIsLoggedIn()) {
                val response = authenticatedApiService.getUserFollowing(
                    username,
                    page,
                    perPage
                )
                return response.toResult()
            } else {
                val response = apiService.getUserFollowing(
                    username,
                    page,
                    perPage
                )
                return response.toResult()
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }

    override suspend fun isFollowingUser(username: String): ApiResponse<Boolean> {
        return try {
            val response = authenticatedApiService.isFollowingUser(username)
            when (response.code()) {
                204 -> ApiResponse.Success(true)
                404 -> ApiResponse.Success(false)
                401 -> ApiResponse.Error(Exception("Unauthorized"))
                else -> ApiResponse.Error(Exception("Unknown error: ${response.code()}"))
            }
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun followUser(username: String): ApiResponse<Boolean> {
        return try {
            val response = authenticatedApiService.followUser(username)
            when (response.code()) {
                204 -> ApiResponse.Success(true)
                404 -> ApiResponse.Success(false)
                401 -> ApiResponse.Error(Exception("Unauthorized"))
                else -> ApiResponse.Error(Exception("Unknown error: ${response.code()}"))
            }
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun unFollowUser(username: String): ApiResponse<Boolean> {
        return try {
            val response = authenticatedApiService.unfollowUser(username)
            when (response.code()) {
                204 -> ApiResponse.Success(true)
                404 -> ApiResponse.Success(false)
                401 -> ApiResponse.Error(Exception("Unauthorized"))
                else -> ApiResponse.Error(Exception("Unknown error: ${response.code()}"))
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }

    override suspend fun getRepoDetail(username: String, repo: String): ApiResponse<RepoDetail> {
        try {
            if (sessionManager.checkIsLoggedIn()) {
                val response = authenticatedApiService.getRepoDetails(
                    username,
                    repo,
                )
                return response.toResult()
            } else {
                val response = apiService.getRepoDetails(
                    username,
                    repo,
                )
                return response.toResult()
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }

    override suspend fun isStarred(username: String, repo: String): ApiResponse<Boolean> {
        return try {
            val response = authenticatedApiService.isStarred(username, repo)
            when (response.code()) {
                204 -> ApiResponse.Success(true)
                404 -> ApiResponse.Success(false)
                401 -> ApiResponse.Error(Exception("Unauthorized"))
                else -> ApiResponse.Error(Exception("Unknown error: ${response.code()}"))
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }

    override suspend fun starRepo(username: String, repo: String): ApiResponse<Boolean> {
        return try {
            val response = authenticatedApiService.starRepo(username, repo)
            when (response.code()) {
                204 -> ApiResponse.Success(true)
                404 -> ApiResponse.Success(false)
                401 -> ApiResponse.Error(Exception("Unauthorized"))
                else -> ApiResponse.Error(Exception("Unknown error: ${response.code()}"))
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }

    override suspend fun unStarRepo(username: String, repo: String): ApiResponse<Boolean> {
        return try {
            val response = authenticatedApiService.unstarRepo(username, repo)
            when (response.code()) {
                204 -> ApiResponse.Success(true)
                404 -> ApiResponse.Success(false)
                401 -> ApiResponse.Error(Exception("Unauthorized"))
                else -> ApiResponse.Error(Exception("Unknown error: ${response.code()}"))
            }
        } catch (e: Exception) {
            return ApiResponse.Error(e)
        }
    }
}