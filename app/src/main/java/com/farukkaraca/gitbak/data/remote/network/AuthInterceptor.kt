package com.farukkaraca.gitbak.data.remote.network

import com.farukkaraca.gitbak.data.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sessionManager.sessionState.value.accessToken
        val requestBuilder = chain.request().newBuilder()

        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer ${it.access_token}")
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("X-GitHub-Api-Version", GITHUB_API_VERSION)
        }

        return chain.proceed(requestBuilder.build())
    }
}

const val GITHUB_API_VERSION = "2022-11-28"