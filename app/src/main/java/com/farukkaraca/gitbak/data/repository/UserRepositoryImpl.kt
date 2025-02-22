package com.farukkaraca.gitbak.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.farukkaraca.gitbak.data.common.toResult
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.GithubRepo
import com.farukkaraca.gitbak.data.model.RepoDetail
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.data.model.UserSearchResponse
import com.farukkaraca.gitbak.data.remote.datasource.FFPagingSource
import com.farukkaraca.gitbak.data.remote.datasource.GithubRepoPagingSource
import com.farukkaraca.gitbak.data.remote.service.GitHubApiService
import com.farukkaraca.gitbak.data.remote.service.GitHubAuthenticatedApiService
import com.farukkaraca.gitbak.data.session.SessionManager
import com.farukkaraca.gitbak.domain.repository.UserRepository
import com.farukkaraca.gitbak.presentation.state.FFType
import kotlinx.coroutines.flow.Flow
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

    override fun getUserRepositories(username: String): Flow<PagingData<GithubRepo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                GithubRepoPagingSource(
                    username = username,
                    apiService = apiService,
                    authenticatedApiService = authenticatedApiService,
                    sessionManager = sessionManager
                )
            }
        ).flow
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

    override fun getUserFollowers(username: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = {
                FFPagingSource(
                    username = username,
                    ffType = FFType.Followers.name,
                    apiService = apiService,
                    authenticatedApiService = authenticatedApiService,
                    sessionManager = sessionManager
                )
            }
        ).flow
    }

    override fun getUserFollowing(username: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = {
                FFPagingSource(
                    username = username,
                    ffType = FFType.Following.name,
                    apiService = apiService,
                    authenticatedApiService = authenticatedApiService,
                    sessionManager = sessionManager
                )
            }
        ).flow
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