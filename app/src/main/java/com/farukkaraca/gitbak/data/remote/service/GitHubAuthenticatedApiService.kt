package com.farukkaraca.gitbak.data.remote.service

import com.farukkaraca.gitbak.data.model.GithubRepo
import com.farukkaraca.gitbak.data.model.RepoDetail
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.data.model.UserSearchResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubAuthenticatedApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): Response<UserSearchResponse>

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): Response<UserDetail>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<GithubRepo>>

    @GET("user")
    suspend fun getCurrentUser(): Response<UserDetail>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<User>>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<User>>

    @GET("user/following/{username}")
    suspend fun isFollowingUser(
        @Path("username") username: String
    ): Response<Unit>

    @PUT("user/following/{username}")
    suspend fun followUser(
        @Path("username") username: String
    ): Response<Unit>

    @DELETE("user/following/{username}")
    suspend fun unfollowUser(
        @Path("username") username: String
    ): Response<Unit>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<RepoDetail>

    @GET("user/starred/{owner}/{repo}")
    suspend fun isStarred(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>

    @PUT("user/starred/{owner}/{repo}")
    suspend fun starRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>

    @DELETE("user/starred/{owner}/{repo}")
    suspend fun unstarRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>
}