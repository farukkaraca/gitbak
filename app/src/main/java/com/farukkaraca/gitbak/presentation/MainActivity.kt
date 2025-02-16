package com.farukkaraca.gitbak.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farukkaraca.gitbak.BuildConfig
import com.farukkaraca.gitbak.presentation.navigation.AppNavigation
import com.farukkaraca.gitbak.presentation.theme.GitBakTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class]

        enableEdgeToEdge()
        setContent {
            val state = mainViewModel.state.collectAsStateWithLifecycle()

            GitBakTheme {
                AppNavigation(
                    state = state.value,
                    onLoginClick = {
                        onClickLogin()
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val uri = intent.data
        uri?.let {
            val authCode = it.getQueryParameter(paramCode)
            authCode?.let { code ->
                mainViewModel.fetchAccessToken(code)
            }
        }
    }

    private fun onClickLogin() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

private const val paramCode = "code"
private const val clientId = BuildConfig.GITHUB_CLIENT_ID
private const val redirectUri = "http://localhost:3000/callback"
const val url =
    "https://github.com/login/oauth/authorize?client_id=$clientId&scope=repo&redirect_url=$redirectUri"