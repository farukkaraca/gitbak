package com.farukkaraca.gitbak.presentation.state

import com.farukkaraca.gitbak.presentation.screens.main.UiEvent

data class MainState(
    val showLoading: Boolean = false,
    val showButtonLoading: Boolean = false,
    val errorAccessToken: Error = Error(),
    val errorCurrentUser: Error = Error(),
    val loginSuccess: Boolean = false,
    val isLogout: Boolean = false,
    val uiEvent: UiEvent?= null,
)