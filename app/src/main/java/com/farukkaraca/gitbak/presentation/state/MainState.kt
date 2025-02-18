package com.farukkaraca.gitbak.presentation.state

import com.farukkaraca.gitbak.presentation.UiEvent

data class MainState(
    val isLoading: Boolean = false,
    val errorAccessToken: Error = Error(),
    val errorCurrentUser: Error = Error(),
    val loginSuccess: Boolean = false,
    val isLogout: Boolean = false,
    val uiEvent: UiEvent ?= null,
)