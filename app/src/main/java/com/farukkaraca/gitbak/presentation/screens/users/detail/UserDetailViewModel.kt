package com.farukkaraca.gitbak.presentation.screens.users.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.data.session.SessionManager
import com.farukkaraca.gitbak.domain.usecase.FollowUserUseCase
import com.farukkaraca.gitbak.domain.usecase.IsFollowingUserUseCase
import com.farukkaraca.gitbak.domain.usecase.UnFollowUserUseCase
import com.farukkaraca.gitbak.domain.usecase.UserDetailUseCase
import com.farukkaraca.gitbak.presentation.state.Error
import com.farukkaraca.gitbak.presentation.state.UserDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val userDetailUseCase: UserDetailUseCase,
    private val isFollowingUserUseCase: IsFollowingUserUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val unFollowUserUseCase: UnFollowUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<UserDetailState>(UserDetailState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                isLoggedIn = sessionManager.checkIsLoggedIn()
            )
        }
    }

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }

            if (!state.value.isLoggedIn) {
                handleUserDetailResult(userDetailUseCase.execute(username))

            } else {
                val userDetailResult = async {
                    userDetailUseCase.execute(username)
                }.await()

                val isFollowingResult = async {
                    isFollowingUserUseCase.execute(username)
                }.await()

                handleUserDetailResult(userDetailResult)
                handleIsFollowingResult(isFollowingResult)
            }
        }
    }

    fun onClickFollowButton(username: String) {
        if (!state.value.isLoggedIn) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoadingFollowUser = true
                )
            }

            if (state.value.isFollowingUser) {
                handleUnFollowResult(unFollowUserUseCase.execute(username))
            } else {
                handleFollowResult(followUserUseCase.execute(username))
            }
        }
    }

    private fun handleUserDetailResult(userDetailResult: ApiResponse<UserDetail>) {
        when (userDetailResult) {
            is ApiResponse.Success -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        userDetail = userDetailResult.data
                    )
                }
            }

            is ApiResponse.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = Error(
                            isError = true,
                            message = userDetailResult.exception.message
                        )
                    )
                }
            }

            else -> {}
        }
    }

    private fun handleIsFollowingResult(isFollowingResult: ApiResponse<Boolean>) {
        when (isFollowingResult) {
            is ApiResponse.Success -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isFollowingUser = isFollowingResult.data
                    )
                }
            }

            is ApiResponse.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = Error(
                            isError = true,
                            message = isFollowingResult.exception.message
                        )
                    )
                }
            }

            else -> {}
        }
    }

    private fun handleFollowResult(followUserResult: ApiResponse<Boolean>) {
        when (followUserResult) {
            is ApiResponse.Success -> {
                _state.update {
                    it.copy(
                        isLoadingFollowUser = false,
                        isFollowingUser = followUserResult.data
                    )
                }
            }

            is ApiResponse.Error -> {
                _state.update {
                    it.copy(
                        isLoadingFollowUser = false,
                        error = Error(
                            isError = true,
                            message = followUserResult.exception.message
                        )
                    )
                }
            }

            else -> {}
        }
    }

    private fun handleUnFollowResult(unFollowUserResult: ApiResponse<Boolean>) {
        when (unFollowUserResult) {
            is ApiResponse.Success -> {
                _state.update {
                    it.copy(
                        isLoadingFollowUser = false,
                        isFollowingUser = !unFollowUserResult.data
                    )
                }
            }

            is ApiResponse.Error -> {
                _state.update {
                    it.copy(
                        isLoadingFollowUser = false,
                        error = Error(
                            isError = true,
                            message = unFollowUserResult.exception.message
                        )
                    )
                }
            }

            else -> {}
        }
    }
}