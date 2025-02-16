package com.farukkaraca.gitbak.data.session

import com.farukkaraca.gitbak.data.model.AccessToken
import com.farukkaraca.gitbak.data.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    private val _sessionState = MutableStateFlow(Session())
    val sessionState: StateFlow<Session> = _sessionState.asStateFlow()

    fun login(accessToken: AccessToken) {
        _sessionState.value = Session(accessToken = accessToken)
    }

    fun logout() {
        _sessionState.value = Session(accessToken = null)
    }

    fun checkIsLoggedIn() : Boolean {
        return _sessionState.value.accessToken != null
    }
}
