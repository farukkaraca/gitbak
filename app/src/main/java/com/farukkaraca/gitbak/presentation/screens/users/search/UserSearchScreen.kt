package com.farukkaraca.gitbak.presentation.screens.users.search

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farukkaraca.gitbak.presentation.components.CustomTopAppBar

@Composable
fun UserSearchScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<UserSearchViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "User Search",
                showBackButton = false
            )
        }
    ) { padding ->
        UserSearchContent(
            padding = padding,
            state = state.value,
            onSearch = {
                viewModel.searchUsers(it)
            },
            onClickUser = { user ->
                user.login?.let { username ->
                    navController.navigate("user_detail/$username")
                }
            },
            resetSearch = {
                viewModel.resetSearch()
            }
        )
    }
}