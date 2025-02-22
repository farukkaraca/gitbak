package com.farukkaraca.gitbak.presentation.state

import androidx.paging.PagingData
import com.farukkaraca.gitbak.data.model.GithubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class UserReposState(
    val isLoading: Boolean = true,
    val repoCount: Int = 0,
    val error: Error = Error(),
    val pagingData: Flow<PagingData<GithubRepo>> = emptyFlow()
)