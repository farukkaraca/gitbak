package com.farukkaraca.gitbak.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farukkaraca.gitbak.presentation.screens.user_repos.UserReposScreen
import com.farukkaraca.gitbak.presentation.screens.users.detail.UserDetailScreen
import com.farukkaraca.gitbak.presentation.screens.users.search.UserSearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "user_screen") {
        composable("user_screen") {
            UserSearchScreen(navController = navController)
        }
        composable("user_detail/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserDetailScreen(navController = navController, username = username)
        }
        composable("user_repos/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserReposScreen(navController = navController, username = username)
        }
    }
}
