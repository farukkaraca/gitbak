package com.farukkaraca.gitbak.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farukkaraca.gitbak.presentation.screens.onboarding.OnboardingScreen
import com.farukkaraca.gitbak.presentation.screens.user_repos.UserReposScreen
import com.farukkaraca.gitbak.presentation.screens.users.detail.UserDetailScreen
import com.farukkaraca.gitbak.presentation.screens.users.search.UserSearchScreen
import com.farukkaraca.gitbak.presentation.state.MainState

@Composable
fun AppNavigation(
    state: MainState,
    onLoginClick: () -> Unit,
) {
    val navController = rememberNavController()

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            navController.navigate(USER_SCREEN) {
                popUpTo(ONBOARDING_SCREEN) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = ONBOARDING_SCREEN
    ) {
        composable(ONBOARDING_SCREEN) {
            OnboardingScreen(
                navController = navController,
                state = state,
                onLoginClick = onLoginClick,
                onContinueWithoutLogin = {
                    navController.navigate(USER_SCREEN) {
                        popUpTo(ONBOARDING_SCREEN) { inclusive = true }
                    }
                }
            )
        }
        composable(USER_SCREEN) {
            UserSearchScreen(navController = navController)
        }
        composable("$USER_DETAIL_SCREEN/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserDetailScreen(navController = navController, username = username)
        }
        composable("$USER_REPOS_SCREEN/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserReposScreen(navController = navController, username = username)
        }
    }
}

const val ONBOARDING_SCREEN = "onboarding"
const val USER_SCREEN = "user_screen"
const val USER_DETAIL_SCREEN = "user_detail"
const val USER_REPOS_SCREEN = "user_repos"
