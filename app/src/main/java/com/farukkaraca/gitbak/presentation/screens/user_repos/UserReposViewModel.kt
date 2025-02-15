package com.farukkaraca.gitbak.presentation.screens.user_repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.domain.usecase.UserReposUseCase
import com.farukkaraca.gitbak.presentation.state.Error
import com.farukkaraca.gitbak.presentation.state.UserReposState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserReposViewModel @Inject constructor(
    private val userReposUseCase: UserReposUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<UserReposState>(UserReposState())
    val state = _state.asStateFlow()

    fun fetchUserRepos(username: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }

            val result = userReposUseCase.execute(
                username = username,
                page = state.value.page,
                perPage = state.value.perPage
            )

            when (result) {
                is ApiResponse.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            repos = result.data
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
}