package com.farukkaraca.gitbak.domain.repository

import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.data.model.UserSearchResponse

interface UserRepository {
    suspend fun searchUsers(query: String): ApiResponse<UserSearchResponse>
    suspend fun getUserDetail(username: String): ApiResponse<UserDetail>
}