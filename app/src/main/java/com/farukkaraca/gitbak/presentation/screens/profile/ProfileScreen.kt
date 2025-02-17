package com.farukkaraca.gitbak.presentation.screens.profile

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farukkaraca.gitbak.data.model.UserDetail
import com.farukkaraca.gitbak.presentation.common.toBottomlessPadding
import com.farukkaraca.gitbak.presentation.components.CustomTopAppBar

@Composable
fun ProfileScreen(
    onClickLogout: () -> Unit,
    onClickFollowers: (UserDetail) -> Unit,
    onClickFollowing: (UserDetail) -> Unit,
    onClickRepos: (UserDetail) -> Unit,
) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    viewModel.getUserDetail()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "My Profile",
                hasAction = true,
                actions = {
                    IconButton(
                        onClick = {
                            onClickLogout()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Logout,
                            contentDescription = "Logout",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { padding ->

        ProfileContent(
            padding = padding.toBottomlessPadding(),
            state = state.value,
            onClickFollowers = {
                state.value.userDetail?.let {
                    onClickFollowers(it)
                }
            },
            onClickFollowing = {
                state.value.userDetail?.let {
                    onClickFollowing(it)
                }
            },
            onClickRepos = {
                state.value.userDetail?.let {
                    onClickRepos(it)
                }
            }
        )
    }
}