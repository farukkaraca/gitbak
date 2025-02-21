package com.farukkaraca.gitbak.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.farukkaraca.gitbak.data.common.ApiConstants
import com.farukkaraca.gitbak.data.remote.network.AuthInterceptor
import com.farukkaraca.gitbak.data.remote.service.GitHubApiService
import com.farukkaraca.gitbak.data.remote.service.GitHubAuthenticatedApiService
import com.farukkaraca.gitbak.data.remote.service.OAuthApiService
import com.farukkaraca.gitbak.data.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MainModules {

    @Provides
    @Singleton
    fun provideSessionManager(): SessionManager {
        return SessionManager()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionManager: SessionManager): AuthInterceptor {
        return AuthInterceptor(sessionManager)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named(AUTHENTICATED_RETROFIT)
    fun provideAuthenticatedRetrofit(
        authInterceptor: AuthInterceptor
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named(OAUTH_RETROFIT)
    fun provideOAuthRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL_AUTH)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOAuthApiService(@Named(OAUTH_RETROFIT) retrofit: Retrofit): OAuthApiService {
        return retrofit.create(OAuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGitHubApiService(retrofit: Retrofit): GitHubApiService {
        return retrofit.create(GitHubApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticatedApiService(
        @Named(AUTHENTICATED_RETROFIT) retrofit: Retrofit
    ): GitHubAuthenticatedApiService {
        return retrofit.create(GitHubAuthenticatedApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        val masterKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "encrypted_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}

const val AUTHENTICATED_RETROFIT = "authenticated"
const val OAUTH_RETROFIT = "OAuthRetrofit"
const val CLIENT_ID = "clientId"
const val CLIENT_SECRET = "clientSecret"

