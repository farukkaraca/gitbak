package com.farukkaraca.gitbak.presentation.state

import androidx.paging.PagingData
import com.farukkaraca.gitbak.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

// followers or following
data class UserFFState(
    val type: String = FFType.None.name,
    val isLoading: Boolean = true,
    val pagingData: Flow<PagingData<User>> = emptyFlow(),
    val error: Error = Error(),
)

enum class FFType {
    Following,
    Followers,
    None,
}