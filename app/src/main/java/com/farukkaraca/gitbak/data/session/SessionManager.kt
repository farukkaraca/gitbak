package com.farukkaraca.gitbak.data.session

import com.farukkaraca.gitbak.data.model.AccessToken
import com.farukkaraca.gitbak.data.model.Session
import com.farukkaraca.gitbak.data.model.UserDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    private val _sessionState = MutableStateFlow(Session())
    val sessionState: StateFlow<Session> = _sessionState.asStateFlow()

    fun setAccessToken(accessToken: AccessToken) {
        _sessionState.update { it.copy(accessToken = accessToken) }
    }

    fun logout() {
        _sessionState.value = Session(accessToken = null, currentUser = null)
    }

    fun checkIsLoggedIn(): Boolean {
        return _sessionState.value.accessToken != null
    }

    fun setCurrentUser(user: UserDetail) {
        _sessionState.update { it.copy(currentUser = user) }
    }
}
