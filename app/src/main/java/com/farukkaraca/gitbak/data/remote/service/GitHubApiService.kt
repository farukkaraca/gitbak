package com.farukkaraca.gitbak.data.remote.service

import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.data.model.UserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): Response<UserSearchResponse>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): Response<UserDetail>
}