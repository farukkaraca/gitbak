package com.farukkaraca.gitbak.presentation.screens.shared.ff

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.presentation.common.toBottomlessPadding
import com.farukkaraca.gitbak.presentation.components.CustomTopAppBar

@Composable
fun FFScreen(
    navController: NavHostController,
    username: String,
    type: String,
    onClickUser: (User) -> Unit
) {
    val viewModel = hiltViewModel<FFScreenViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    viewModel.setType(type)

    LaunchedEffect (username) {
        viewModel.fetchUsers(username, state.value.page)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "${username.capitalize()} $type",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { padding ->

        FFContent(
            padding = padding.toBottomlessPadding(),
            state = state.value,
            onClickUser = {
                onClickUser(it)
            },
            onScroll = { page ->
                viewModel.fetchUsers(username, page)
            }
        )
    }
}