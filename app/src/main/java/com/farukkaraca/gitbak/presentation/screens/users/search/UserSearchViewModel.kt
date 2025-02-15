package com.farukkaraca.gitbak.presentation.screens.users.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.domain.usecase.SearchUsersUseCase
import com.farukkaraca.gitbak.presentation.state.Error
import com.farukkaraca.gitbak.presentation.state.UserSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UserSearchState>(UserSearchState())
    val state = _state.asStateFlow()

    fun searchUsers(query: String) {
        viewModelScope.launch {

            if (query.isBlank()) {
                resetSearch()
                return@launch
            }

            _state.update {
                it.copy(
                    isLoading = true,
                )
            }

            val result = searchUsersUseCase.execute(query)

            when (result) {
                is ApiResponse.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            userSearchResponse = result.data
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

    fun resetSearch() {
        _state.update {
            it.copy(
                userSearchResponse = null
            )
        }
    }
}