package com.farukkaraca.gitbak.presentation.state

import com.farukkaraca.gitbak.data.model.UserDetail

data class ProfileState(
    val isLoading: Boolean = true,
    val userDetail: UserDetail? = null,
    val error: Error = Error(),
)