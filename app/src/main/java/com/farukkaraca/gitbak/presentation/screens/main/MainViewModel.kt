package com.farukkaraca.gitbak.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukkaraca.gitbak.data.model.AccessToken
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.session.SessionManager
import com.farukkaraca.gitbak.domain.usecase.ClearAccessTokenUseCase
import com.farukkaraca.gitbak.domain.usecase.CurrentUserUseCase
import com.farukkaraca.gitbak.domain.usecase.GetAccessTokenUseCase
import com.farukkaraca.gitbak.domain.usecase.GithubAuthUseCase
import com.farukkaraca.gitbak.domain.usecase.SaveAccessTokenUseCase
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
    private val sessionManager: SessionManager,
    private val githubAuthUseCase: GithubAuthUseCase,
    private val getCurrentUserUseCase: CurrentUserUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val clearAccessTokenUseCase: ClearAccessTokenUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState())
    val state = _state.asStateFlow()

    fun getAccessTokenFromDevice() {
        viewModelScope.launch {
            setMainLoading(true)

            val accessToken = getAccessTokenUseCase.execute()

            if (!accessToken.isNullOrBlank()) {
                sessionManager.setAccessToken(AccessToken(access_token = accessToken))

                val fetchUserResult = getCurrentUserUseCase.execute()
                if (fetchUserResult is ApiResponse.Error) {
                    _state.update {
                        it.copy(
                            errorCurrentUser = Error(
                                isError = true,
                                message = fetchUserResult.exception.message
                            )
                        )
                    }
                    showError("Error when signing. Please use signing with Github button")
                    return@launch
                }

                val user = (fetchUserResult as ApiResponse.Success).data
                sessionManager.setCurrentUser(user)

                setMainLoading(false)
                setSuccess(true)
            } else {
                setMainLoading(false)
            }
        }
    }

    private fun saveAccessToken(token: String) {
        saveAccessTokenUseCase.execute(token)
    }

    fun fetchAccessTokenAndUser(authCode: String) {
        if (sessionManager.checkIsLoggedIn()) {
            return
        }

        setLoading(true)

        viewModelScope.launch {
            val fetchAccessTokenResult = githubAuthUseCase.execute(authCode)
            if (fetchAccessTokenResult is ApiResponse.Error) {
                _state.update {
                    it.copy(
                        errorAccessToken = Error(
                            isError = true,
                            message = fetchAccessTokenResult.exception.message
                        )
                    )
                }
                showError("Error when signing")
                return@launch
            }

            val accessToken = (fetchAccessTokenResult as ApiResponse.Success).data
            sessionManager.setAccessToken(accessToken)

            val fetchUserResult = getCurrentUserUseCase.execute()
            if (fetchUserResult is ApiResponse.Error) {
                _state.update {
                    it.copy(
                        errorCurrentUser = Error(
                            isError = true,
                            message = fetchUserResult.exception.message
                        )
                    )
                }
                showError("Error when signing")
                return@launch
            }

            val user = (fetchUserResult as ApiResponse.Success).data
            sessionManager.setCurrentUser(user)

            setLoading(false)
            setSuccess(true)
            saveAccessToken(accessToken.access_token)
        }
    }

    private fun setMainLoading(isLoading: Boolean) {
        _state.update {
            it.copy(
                showLoading = isLoading
            )
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update {
            it.copy(
                showButtonLoading = isLoading
            )
        }
    }

    private fun setSuccess(isSuccess: Boolean) {
        _state.update {
            it.copy(
                loginSuccess = isSuccess
            )
        }
    }

    fun logout() {
        sessionManager.logout()
        _state.update {
            it.copy(
                isLogout = true,
                loginSuccess = false
            )
        }
        clearAccessTokenUseCase.execute()
    }

    fun onUiEventHandled() {
        _state.update {
            it.copy(
                uiEvent = null
            )
        }
    }

    private fun showError(error: String) {
        _state.update {
            it.copy(
                uiEvent = UiEvent.ShowToast(error)
            )
        }
    }
}

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
}