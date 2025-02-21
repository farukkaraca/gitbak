package com.farukkaraca.gitbak.presentation.state

import com.farukkaraca.gitbak.data.model.User

// followers or following
data class UserFFState(
    val type: String = FFType.None.name,
    val isLoading: Boolean = true,
    val isScroll: Boolean = false,
    val page: Int = 1,
    val perPage: Int = 30,
    val users: List<User> = emptyList(),
    val error: Error = Error(),
)

enum class FFType {
    Following,
    Followers,
    None,
}