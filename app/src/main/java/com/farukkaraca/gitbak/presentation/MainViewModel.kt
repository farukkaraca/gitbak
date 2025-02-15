package com.farukkaraca.gitbak.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.domain.usecase.SearchUsersUseCase
import com.farukkaraca.gitbak.presentation.state.Error
import com.farukkaraca.gitbak.presentation.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase
) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState())
    val mainState = _mainState.asStateFlow()

    fun searchUsers(query: String) {
        viewModelScope.launch {

            if (query.isBlank()) {
                resetSearch()
                return@launch
            }

            _mainState.update {
                it.copy(
                    isLoading = true,
                )
            }

            val result = searchUsersUseCase.execute(query)

            when (result) {
                is ApiResponse.Success -> {
                    _mainState.update {
                        it.copy(
                            isLoading = false,
                            userSearchResponse = result.data
                        )
                    }
                }

                is ApiResponse.Error -> {
                    _mainState.update {
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
        _mainState.update {
            it.copy(
                userSearchResponse = null
            )
        }
    }
}