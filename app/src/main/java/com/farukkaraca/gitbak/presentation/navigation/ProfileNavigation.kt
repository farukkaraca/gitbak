package com.farukkaraca.gitbak.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.farukkaraca.gitbak.presentation.screens.profile.ProfileScreen
import com.farukkaraca.gitbak.presentation.screens.shared.ff.FFScreen
import com.farukkaraca.gitbak.presentation.screens.user_repos.UserReposScreen
import com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail.RepoDetailScreen
import com.farukkaraca.gitbak.presentation.screens.users.detail.UserDetailScreen
import com.farukkaraca.gitbak.presentation.state.FFType

@Composable
fun ProfileNavigation(
    navController: NavHostController,
    onClickLogout: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = PROFILE_SCREEN
    ) {
        composable(PROFILE_SCREEN) {
            ProfileScreen(
                onClickLogout = {
                    onClickLogout()
                },
                onClickFollowers = {
                    navController.navigate("$FOLLOWERS_SCREEN/${it.login}")
                },
                onClickFollowing = {
                    navController.navigate("$FOLLOWING_SCREEN/${it.login}")
                },
                onClickRepos = {
                    navController.navigate("$USER_REPOS_SCREEN/${it.login}")
                }
            )
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

        composable("$USER_DETAIL_SCREEN/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserDetailScreen(navController = navController, username = username)
        }

        composable("$USER_REPOS_SCREEN/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserReposScreen(navController = navController, username = username)
        }

        composable("$USER_REPO_DETAIL/{username}/{repo}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val repo = backStackEntry.arguments?.getString("repo") ?: ""
            RepoDetailScreen(navController = navController, username = username, repo = repo)
        }
    }
}