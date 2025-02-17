package com.farukkaraca.gitbak.presentation.screens.user_repos

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farukkaraca.gitbak.presentation.common.toBottomlessPadding
import com.farukkaraca.gitbak.presentation.components.CustomTopAppBar

@Composable
fun UserReposScreen(navController: NavController, username: String) {
    val viewModel = hiltViewModel<UserReposViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(username) {
        viewModel.fetchUserRepos(username = username, page = state.value.page)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "${username.capitalize()} Repositories",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { padding ->

        UserReposContent(
            padding = padding.toBottomlessPadding(),
            state = state.value,
            onScroll = { page ->
                viewModel.fetchUserRepos(username, page)
            }
        )
    }
}