package com.farukkaraca.gitbak.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.farukkaraca.gitbak.data.common.toResult
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.data.remote.service.GitHubApiService
import com.farukkaraca.gitbak.data.remote.service.GitHubAuthenticatedApiService
import com.farukkaraca.gitbak.data.session.SessionManager
import com.farukkaraca.gitbak.presentation.state.FFType

class FFPagingSource(
    private val username: String,
    private val ffType: String,
    private val apiService: GitHubApiService,
    private val authenticatedApiService: GitHubAuthenticatedApiService,
    private val sessionManager: SessionManager
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val response = if (sessionManager.checkIsLoggedIn()) {
                if (ffType == FFType.Followers.name) {
                    authenticatedApiService.getUserFollowers(username, page, params.loadSize)
                } else {
                    authenticatedApiService.getUserFollowing(username, page, params.loadSize)
                }
            } else {
                if (ffType == FFType.Followers.name) {
                    apiService.getUserFollowers(username, page, params.loadSize)
                } else {
                    apiService.getUserFollowing(username, page, params.loadSize)
                }
            }

            val result = response.toResult()

            if (result is ApiResponse.Success) {
                LoadResult.Page(
                    data = result.data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (result.data.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("Failed to load repositories"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

