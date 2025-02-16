package com.farukkaraca.gitbak.presentation.screens.shared.ff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukkaraca.gitbak.data.model.ApiResponse
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

    fun fetchUsers(username: String, page: Int) {
        viewModelScope.launch {
            if (state.value.type == FFType.None.name) {
                return@launch
            }

            val result = if (state.value.type == FFType.Followers.name) {
                followingUseCase.execute(
                    username = username,
                    page = page,
                    perPage = state.value.perPage
                )
            } else {
                followersUseCase.execute(
                    username = username,
                    page = page,
                    perPage = state.value.perPage
                )
            }


            when (result) {
                is ApiResponse.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            users = result.data
                        )
                    }
                }

                is ApiResponse.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = Error(
                                isError = true,
                                message = result.exception.message
                            )
                        )
                    }
                }

                else -> {}
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