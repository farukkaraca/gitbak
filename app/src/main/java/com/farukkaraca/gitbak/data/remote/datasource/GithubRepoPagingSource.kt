package com.farukkaraca.gitbak.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.farukkaraca.gitbak.data.common.toResult
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.GithubRepo
import com.farukkaraca.gitbak.data.remote.service.GitHubApiService
import com.farukkaraca.gitbak.data.remote.service.GitHubAuthenticatedApiService
import com.farukkaraca.gitbak.data.session.SessionManager

class GithubRepoPagingSource(
    private val username: String,
    private val apiService: GitHubApiService,
    private val authenticatedApiService: GitHubAuthenticatedApiService,
    private val sessionManager: SessionManager
) : PagingSource<Int, GithubRepo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepo> {
        return try {
            val page = params.key ?: 1
            val response = if (sessionManager.checkIsLoggedIn()) {
                authenticatedApiService.getUserRepositories(username, page, params.loadSize)
            } else {
                apiService.getUserRepositories(username, page, params.loadSize)
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

    override fun getRefreshKey(state: PagingState<Int, GithubRepo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

