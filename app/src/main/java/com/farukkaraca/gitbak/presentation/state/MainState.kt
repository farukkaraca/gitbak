package com.farukkaraca.gitbak.presentation.state

data class MainState(
    val isLoading: Boolean = false,
    val error: Error = Error(),
)