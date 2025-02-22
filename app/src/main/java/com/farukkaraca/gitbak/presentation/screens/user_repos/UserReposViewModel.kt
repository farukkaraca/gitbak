package com.farukkaraca.gitbak.presentation.screens.user_repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
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
    private val _state = MutableStateFlow(UserReposState())
    val state = _state.asStateFlow()

    fun fetchUserRepos(username: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val pagingDataFlow = userReposUseCase.execute(username)
                    .cachedIn(viewModelScope)

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
}