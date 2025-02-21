package com.farukkaraca.gitbak.presentation.screens.main

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.farukkaraca.gitbak.BuildConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class]
        mainViewModel.getAccessTokenFromDevice()
        enableEdgeToEdge()
        setContent { MainScreen(mainViewModel) }
    }

    override fun onResume() {
        super.onResume()
        handleAuthRedirect(intent.data)
    }

    private fun handleAuthRedirect(uri: Uri?) {
        uri?.getQueryParameter(PARAM_CODE)?.let { code ->
            mainViewModel.fetchAccessTokenAndUser(code)
        }
    }
}

private const val PARAM_CODE = "code"
private const val CLIENT_ID = BuildConfig.GITHUB_CLIENT_ID
private const val REDIRECT_URI = "http://localhost:3000/callback"
const val AUTH_URL = "https://github.com/login/oauth/authorize?" +
        "client_id=$CLIENT_ID&" +
        "scope=repo,user&" +
        "redirect_uri=$REDIRECT_URI"