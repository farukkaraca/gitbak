package com.farukkaraca.gitbak.di

import android.content.SharedPreferences
import com.farukkaraca.gitbak.data.repository.TokenRepositoryImpl
import com.farukkaraca.gitbak.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    @Provides
    @Singleton
    fun provideTokenRepository(sharedPreferences: SharedPreferences): TokenRepository {
        return TokenRepositoryImpl(sharedPreferences)
    }
}
