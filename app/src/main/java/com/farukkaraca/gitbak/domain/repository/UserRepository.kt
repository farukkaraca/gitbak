package com.farukkaraca.gitbak.domain.repository

import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.GithubRepo
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.data.model.UserSearchResponse

interface UserRepository {
    suspend fun searchUsers(query: String): ApiResponse<UserSearchResponse>
    suspend fun getUserDetail(username: String): ApiResponse<UserDetail>
    suspend fun getUserRepositories(
        username: String,
        page: Int,
        perPage: Int
    ): ApiResponse<List<GithubRepo>>

    suspend fun getUserProfile(): ApiResponse<UserDetail>
    suspend fun getUserFollowers(
        username: String,
        page: Int,
        perPage: Int
    ): ApiResponse<List<User>>

    suspend fun getUserFollowing(
        username: String,
        page: Int,
        perPage: Int
    ): ApiResponse<List<User>>

    suspend fun isFollowingUser(username: String): ApiResponse<Boolean>
    suspend fun followUser(username: String): ApiResponse<Boolean>
    suspend fun unFollowUser(username: String): ApiResponse<Boolean>
}