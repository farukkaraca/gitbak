package com.farukkaraca.gitbak.presentation.screens.users.detail

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farukkaraca.gitbak.presentation.components.CustomTopAppBar
import com.farukkaraca.gitbak.presentation.navigation.USER_REPOS_SCREEN

@Composable
fun UserDetailScreen(navController: NavController, username: String) {
    val viewModel = hiltViewModel<UserDetailViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    viewModel.getUserDetail(username)

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "User Detail",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { padding ->

        UserDetailContent(
            padding = padding,
            state = state.value,
            onClickFollowers = {

            },
            onClickFollowing = {

            },
            onClickRepos = {
                navController.navigate("$USER_REPOS_SCREEN/$username")
            }
        )
    }
}