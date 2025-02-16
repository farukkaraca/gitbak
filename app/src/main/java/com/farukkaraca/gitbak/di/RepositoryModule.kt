package com.farukkaraca.gitbak.di

import com.farukkaraca.gitbak.BuildConfig
import com.farukkaraca.gitbak.data.remote.service.GitHubApiService
import com.farukkaraca.gitbak.data.remote.service.GitHubAuthenticatedApiService
import com.farukkaraca.gitbak.data.remote.service.OAuthApiService
import com.farukkaraca.gitbak.data.repository.GitHubAuthRepositoryImpl
import com.farukkaraca.gitbak.data.repository.UserRepositoryImpl
import com.farukkaraca.gitbak.data.session.SessionManager
import com.farukkaraca.gitbak.domain.repository.GitHubAuthRepository
import com.farukkaraca.gitbak.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRepository(
        apiService: GitHubApiService,
        authenticatedApiService: GitHubAuthenticatedApiService,
        sessionManager: SessionManager
    ): UserRepository {
        return UserRepositoryImpl(apiService, authenticatedApiService, sessionManager)
    }

    @Provides
    @Named(CLIENT_ID)
    fun provideClientId(): String {
        return BuildConfig.GITHUB_CLIENT_ID
    }

    @Provides
    @Named(CLIENT_SECRET)
    fun provideClientSecret(): String {
        return BuildConfig.GITHUB_CLIENT_SECRET
    }

    @Provides
    fun provideGitHubAuthRepository(
        oAuthApiService: OAuthApiService,
        @Named(CLIENT_ID) clientId: String,
        @Named(CLIENT_SECRET) clientSecret: String
    ): GitHubAuthRepository {
        return GitHubAuthRepositoryImpl(oAuthApiService, clientId, clientSecret)
    }
}