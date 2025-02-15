package com.farukkaraca.gitbak.presentation.state

import com.farukkaraca.gitbak.data.model.GithubRepo

data class UserReposState(
    val isLoading: Boolean = true,
    val isScroll: Boolean = false,
    val page: Int = 1,
    val perPage: Int = 10,
    val repos: List<GithubRepo> = emptyList(),
    val error: Error = Error(),
)