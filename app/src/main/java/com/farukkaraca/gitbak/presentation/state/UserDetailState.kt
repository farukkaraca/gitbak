package com.farukkaraca.gitbak.presentation.state

import com.farukkaraca.gitbak.data.model.UserDetail

data class UserDetailState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val userDetail: UserDetail? = null,
    val error: Error = Error(),
    val isFollowingUser: Boolean = false,
    val isLoadingFollowUser: Boolean = false,
)