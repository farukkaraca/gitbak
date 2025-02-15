package com.farukkaraca.gitbak.presentation.state

data class Error(
    val isError: Boolean = false,
    val message: String? = null,
)