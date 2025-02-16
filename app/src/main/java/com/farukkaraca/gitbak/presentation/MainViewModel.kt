package com.farukkaraca.gitbak.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukkaraca.gitbak.data.model.AccessToken
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.session.SessionManager
import com.farukkaraca.gitbak.domain.usecase.GithubAuthUseCase
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
    private val githubAuthUseCase: GithubAuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MainState>(MainState())
    val state = _state.asStateFlow()

    fun userIsLogging(): Boolean {
        return sessionManager.checkIsLoggedIn()
    }

    fun login(accessToken: AccessToken) {
        sessionManager.login(accessToken)
    }

    fun fetchAccessToken(autCode: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }

            val result = githubAuthUseCase.execute(autCode)

            when (result) {
                is ApiResponse.Success -> {
                    login(
                        result.data
                    )

                    _state.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true
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
