package com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukkaraca.gitbak.data.model.ApiResponse
import com.farukkaraca.gitbak.data.session.SessionManager
import com.farukkaraca.gitbak.domain.usecase.IsRepoStarredUseCase
import com.farukkaraca.gitbak.domain.usecase.RepoDetailUseCase
import com.farukkaraca.gitbak.domain.usecase.StarRepoUseCase
import com.farukkaraca.gitbak.domain.usecase.UnStarRepoUseCase
import com.farukkaraca.gitbak.presentation.state.Error
import com.farukkaraca.gitbak.presentation.state.RepoDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repoDetailUseCase: RepoDetailUseCase,
    private val isRepoStarredUseCase: IsRepoStarredUseCase,
    private val starRepoUseCase: StarRepoUseCase,
    private val unStarRepoUseCase: UnStarRepoUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<RepoDetailState>(RepoDetailState())
    val state = _state.asStateFlow()


    init {
        _state.update {
            it.copy(
                isLoggedIn = sessionManager.checkIsLoggedIn(),
                currentUsername = sessionManager.sessionState.value.currentUser?.login
            )
        }
    }

    fun getRepoDetailAndIsStarred(username: String, repo: String) {
        viewModelScope.launch {
            coroutineScope {
                val repoDetailDeferred = async { repoDetailUseCase.execute(username, repo) }
                val isStarredDeferred = async { isRepoStarredUseCase.execute(username, repo) }

                val repoDetailResult = repoDetailDeferred.await()
                val isStarredResult = isStarredDeferred.await()

                _state.update { it.copy(isLoading = false) }

                when (repoDetailResult) {
                    is ApiResponse.Success -> {
                        _state.update {
                            it.copy(repoDetail = repoDetailResult.data)
                        }
                    }

                    is ApiResponse.Error -> {
                        _state.update {
                            it.copy(
                                errorFetchRepoDetail = Error(
                                    isError = true,
                                    message = repoDetailResult.exception.message
                                )
                            )
                        }
                    }

                    else -> {}
                }

                when (isStarredResult) {
                    is ApiResponse.Success -> {
                        _state.update {
                            it.copy(isStarred = isStarredResult.data)
                        }
                    }

                    is ApiResponse.Error -> {
                        _state.update {
                            it.copy(
                                errorIsStarredRepo = Error(
                                    isError = true,
                                    message = isStarredResult.exception.message
                                )
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun starUnStarRepo(username: String, repo: String) {
        if (state.value.isStarred) {
            unStartRepo(username, repo)
        } else {
            starRepo(username, repo)
        }
    }

    private fun starRepo(username: String, repo: String) {
        viewModelScope.launch {
            val result = starRepoUseCase.execute(username, repo)
            when (result) {
                is ApiResponse.Success -> {
                    _state.update {
                        it.copy(
                            isLoadingStarRepo = false,
                            isStarred = result.data
                        )
                    }

                    getRepoDetailAndIsStarred(username, repo)
                }

                is ApiResponse.Error -> {
                    _state.update {
                        it.copy(
                            isLoadingStarRepo = false,
                            errorStarRepo = Error(
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

    private fun unStartRepo(username: String, repo: String) {
        viewModelScope.launch {
            val result = unStarRepoUseCase.execute(username, repo)
            when (result) {
                is ApiResponse.Success -> {
                    _state.update {
                        it.copy(
                            isLoadingUnStarRepo = false,
                            isStarred = !result.data
                        )
                    }

                    getRepoDetailAndIsStarred(username, repo)
                }

                is ApiResponse.Error -> {
                    _state.update {
                        it.copy(
                            isLoadingUnStarRepo = false,
                            errorUnStarRepo = Error(
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