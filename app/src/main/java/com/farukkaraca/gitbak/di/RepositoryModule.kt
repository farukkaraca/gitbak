package com.farukkaraca.gitbak.di

import com.farukkaraca.gitbak.data.remote.service.GitHubApiService
import com.farukkaraca.gitbak.data.repository.UserRepositoryImpl
import com.farukkaraca.gitbak.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(apiService: GitHubApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }
}