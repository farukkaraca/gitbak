package com.farukkaraca.gitbak.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.farukkaraca.gitbak.BuildConfig
import com.farukkaraca.gitbak.presentation.navigation.AppNavigation
import com.farukkaraca.gitbak.presentation.navigation.BottomNavigation
import com.farukkaraca.gitbak.presentation.navigation.ONBOARDING_SCREEN
import com.farukkaraca.gitbak.presentation.navigation.USER_SEARCH_SCREEN
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
            val usersNavController = rememberNavController()
            val profileNavController = rememberNavController()

            LaunchedEffect(state.value.uiEvent) {
                state.value.uiEvent?.let { event ->
                    when (event) {
                        is UiEvent.ShowToast -> {
                            Toast.makeText(baseContext, event.message, Toast.LENGTH_SHORT).show()
                            mainViewModel.onUiEventHandled()
                        }
                    }
                }
            }

            LaunchedEffect(state.value.loginSuccess) {
                if (state.value.loginSuccess) {
                    usersNavController.navigate(USER_SEARCH_SCREEN) {
                        popUpTo(ONBOARDING_SCREEN) { inclusive = true }
                    }
                }
            }

            LaunchedEffect(state.value.isLogout) {
                if (state.value.isLogout) {
                    usersNavController.navigate(ONBOARDING_SCREEN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }

            GitBakTheme {
                if (state.value.loginSuccess) {
                    BottomNavigation(
                        state = state.value,
                        navController = usersNavController,
                        profileNavController = profileNavController,
                        onLoginClick = {
                            onClickLogin()
                        },
                        onClickLogout = {
                            mainViewModel.logout()
                        },
                    )
                } else {
                    AppNavigation(
                        navController = usersNavController,
                        state = state.value,
                        onLoginClick = {
                            onClickLogin()
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val uri = intent.data
        uri?.let {
            val authCode = it.getQueryParameter(paramCode)
            authCode?.let { code ->
                mainViewModel.fetchAccessTokenAndUser(code)
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
    "https://github.com/login/oauth/authorize?client_id=$clientId&scope=repo,user&redirect_uri=$redirectUri"