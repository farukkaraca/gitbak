package com.farukkaraca.gitbak.presentation.state

import com.farukkaraca.gitbak.data.model.UserSearchResponse

data class MainState(
    val isLoading: Boolean = false,
    val userSearchResponse: UserSearchResponse? = null,
    val error: Error? = null
)

data class Error(
    val isError: Boolean = false,
    val message: String? = null,
)