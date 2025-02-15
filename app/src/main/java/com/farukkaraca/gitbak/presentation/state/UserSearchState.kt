package com.farukkaraca.gitbak.presentation.state

import com.farukkaraca.gitbak.data.model.UserSearchResponse

data class UserSearchState(
    val isLoading: Boolean = false,
    val userSearchResponse: UserSearchResponse? = null,
    val error: Error = Error(),
)