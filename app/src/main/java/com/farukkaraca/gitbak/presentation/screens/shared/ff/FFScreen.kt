package com.farukkaraca.gitbak.presentation.screens.shared.ff

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.farukkaraca.gitbak.data.model.User
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
    viewModel.fetchUsers(username, state.value.page)

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

        FFContent(
            padding = padding,
            state = state.value,
            onClickUser = {
                onClickUser(it)
            }
        )
    }
}