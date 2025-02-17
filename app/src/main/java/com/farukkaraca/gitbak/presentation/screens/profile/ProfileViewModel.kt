package com.farukkaraca.gitbak.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.domain.usecase.GetUserProfileUseCase
import com.farukkaraca.gitbak.presentation.state.Error
import com.farukkaraca.gitbak.presentation.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: GetUserProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState())
    val state = _state.asStateFlow()

    fun getUserDetail() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }

            val result = profileUseCase.execute()

            when (result) {
                is ApiResponse.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            userDetail = result.data
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
