package com.farukkaraca.gitbak.presentation.state

import com.farukkaraca.gitbak.data.model.RepoDetail

data class RepoDetailState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val isLoadingIsRepoStarred: Boolean = false,
    val isLoadingStarRepo: Boolean = false,
    val isLoadingUnStarRepo: Boolean = false,
    val isStarred: Boolean = false,
    val errorFetchRepoDetail: Error = Error(),
    val errorIsStarredRepo: Error = Error(),
    val errorStarRepo: Error = Error(),
    val errorUnStarRepo: Error = Error(),
    val repoDetail: RepoDetail? = null,
    val isStarLoading: Boolean = false,
    val currentUsername: String? = null,
)