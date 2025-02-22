package com.farukkaraca.gitbak.presentation.screens.shared.ff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.farukkaraca.gitbak.domain.usecase.UserFollowersUseCase
import com.farukkaraca.gitbak.domain.usecase.UserFollowingUseCase
import com.farukkaraca.gitbak.presentation.state.Error
import com.farukkaraca.gitbak.presentation.state.FFType
import com.farukkaraca.gitbak.presentation.state.UserFFState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FFScreenViewModel @Inject constructor(
    private val followingUseCase: UserFollowingUseCase,
    private val followersUseCase: UserFollowersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<UserFFState>(UserFFState())
    val state = _state.asStateFlow()

    fun fetchUsers(username: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val pagingDataFlow = if (state.value.type == FFType.Followers.name) {
                    followersUseCase.execute(username)
                        .cachedIn(viewModelScope)
                } else {
                    followingUseCase.execute(username)
                        .cachedIn(viewModelScope)
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        pagingData = pagingDataFlow
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = Error(
                            isError = true,
                            message = e.message
                        )
                    )
                }
            }
        }
    }

    fun setType(type: String) {
        _state.update {
            it.copy(
                type = type
            )
        }
    }
}