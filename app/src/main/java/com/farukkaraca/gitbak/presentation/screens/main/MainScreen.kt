package com.farukkaraca.gitbak.presentation.screens.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukkaraca.gitbak.presentation.navigation.AppNavigation
import com.farukkaraca.gitbak.presentation.navigation.BottomNavigation
import com.farukkaraca.gitbak.presentation.navigation.ONBOARDING_SCREEN
import com.farukkaraca.gitbak.presentation.navigation.USER_SEARCH_SCREEN
import com.farukkaraca.gitbak.presentation.state.MainState
import com.farukkaraca.gitbak.presentation.theme.GitBakTheme

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val usersNavController = rememberNavController()
    val profileNavController = rememberNavController()

    HandleUiEvents(state.uiEvent, viewModel)
    HandleNavigation(state, usersNavController)

    GitBakTheme {
        if (state.loginSuccess) {
            BottomNavigation(
                state = state,
                navController = usersNavController,
                profileNavController = profileNavController,
                onLoginClick = { openGithubLogin(context) },
                onClickLogout = { viewModel.logout() },
            )
        } else {
            AppNavigation(
                navController = usersNavController,
                state = state,
                onLoginClick = { openGithubLogin(context) }
            )
        }
    }
}

@Composable
private fun HandleUiEvents(
    uiEvent: UiEvent?,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(uiEvent) {
        uiEvent?.let {
            when (it) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    viewModel.onUiEventHandled()
                }
            }
        }
    }
}

@Composable
private fun HandleNavigation(
    state: MainState,
    navController: NavController
) {
    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            navController.navigate(USER_SEARCH_SCREEN) {
                popUpTo(ONBOARDING_SCREEN) { inclusive = true }
            }
        }
    }

    LaunchedEffect(state.isLogout) {
        if (state.isLogout) {
            navController.navigate(ONBOARDING_SCREEN) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
}

private fun openGithubLogin(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AUTH_URL))
    context.startActivity(intent)
}
