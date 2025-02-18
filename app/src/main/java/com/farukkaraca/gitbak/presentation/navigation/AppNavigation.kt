package com.farukkaraca.gitbak.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.farukkaraca.gitbak.presentation.screens.onboarding.OnboardingScreen
import com.farukkaraca.gitbak.presentation.screens.shared.ff.FFScreen
import com.farukkaraca.gitbak.presentation.screens.user_repos.UserReposScreen
import com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail.RepoDetailScreen
import com.farukkaraca.gitbak.presentation.screens.users.detail.UserDetailScreen
import com.farukkaraca.gitbak.presentation.screens.users.search.UserSearchScreen
import com.farukkaraca.gitbak.presentation.state.FFType
import com.farukkaraca.gitbak.presentation.state.MainState

@Composable
fun AppNavigation(
    navController: NavHostController,
    state: MainState,
    onLoginClick: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ONBOARDING_SCREEN
    ) {
        composable(ONBOARDING_SCREEN) {
            OnboardingScreen(
                navController = navController,
                state = state,
                onLoginClick = onLoginClick,
            )
        }

        composable(USER_SEARCH_SCREEN) {
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

        composable("$FOLLOWERS_SCREEN/{username}") { backStackEntry ->
            val username = (backStackEntry.arguments?.getString("username") ?: "")
            FFScreen(
                navController = navController,
                username = username,
                type = FFType.Followers.name,
                onClickUser = {
                    navController.navigate("$USER_DETAIL_SCREEN/${it.login}")
                }
            )
        }

        composable("$FOLLOWING_SCREEN/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            FFScreen(
                navController = navController,
                username = username,
                type = FFType.Following.name,
                onClickUser = {
                    navController.navigate("$USER_DETAIL_SCREEN/${it.login}")
                }
            )
        }

        composable("$USER_REPO_DETAIL/{username}/{repo}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val repo = backStackEntry.arguments?.getString("repo") ?: ""
            RepoDetailScreen(navController = navController, username = username, repo = repo)
        }
    }
}

