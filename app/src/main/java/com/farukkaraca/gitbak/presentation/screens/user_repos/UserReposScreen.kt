package com.farukkaraca.gitbak.presentation.screens.user_repos

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farukkaraca.gitbak.presentation.components.CustomTopAppBar

@Composable
fun UserReposScreen(navController: NavController, username: String) {
    val viewModel = hiltViewModel<UserReposViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    viewModel.fetchUserRepos(username)

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "User Repositories",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { padding ->

        UserReposContent(
            padding = padding,
            state = state.value
        )
    }
}