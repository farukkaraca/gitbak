package com.farukkaraca.gitbak.domain.repository

import androidx.paging.PagingData
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.GithubRepo
import com.farukkaraca.gitbak.data.model.RepoDetail
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.data.model.UserSearchResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun searchUsers(query: String): ApiResponse<UserSearchResponse>
    suspend fun getUserDetail(username: String): ApiResponse<UserDetail>
    fun getUserRepositories(username: String): Flow<PagingData<GithubRepo>>
    suspend fun getCurrentUser(): ApiResponse<UserDetail>
    fun getUserFollowers(username: String): Flow<PagingData<User>>
    fun getUserFollowing(username: String): Flow<PagingData<User>>
    suspend fun isFollowingUser(username: String): ApiResponse<Boolean>
    suspend fun followUser(username: String): ApiResponse<Boolean>
    suspend fun unFollowUser(username: String): ApiResponse<Boolean>
    suspend fun getRepoDetail(username: String, repo: String): ApiResponse<RepoDetail>
    suspend fun isStarred(username: String, repo: String): ApiResponse<Boolean>
    suspend fun starRepo(username: String, repo: String): ApiResponse<Boolean>
    suspend fun unStarRepo(username: String, repo: String): ApiResponse<Boolean>
}