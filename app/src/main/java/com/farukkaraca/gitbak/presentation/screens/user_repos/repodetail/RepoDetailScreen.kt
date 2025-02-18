package com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farukkaraca.gitbak.presentation.components.CustomTopAppBar
import com.farukkaraca.gitbak.presentation.components.InfoText
import com.farukkaraca.gitbak.presentation.components.LoadingAnimation

@Composable
fun RepoDetailScreen(
    navController: NavController,
    username: String,
    repo: String,
) {
    val viewModel = hiltViewModel<RepoDetailViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(username, repo) {
        viewModel.getRepoDetailAndIsStarred(username = username, repo = repo)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Repo Detail",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.value.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation()
                    }
                }

                state.value.errorFetchRepoDetail.isError -> {
                    InfoText(
                        text = "Error: ${state.value.errorFetchRepoDetail.message}",
                        showIcon = true
                    )
                }

                state.value.repoDetail != null -> {
                    RepoDetailContent(
                        repoDetail = state.value.repoDetail!!,
                        isUserLoggedIn = state.value.isLoggedIn,
                        owner = state.value.currentUsername,
                        isStarred = state.value.isStarred,
                        isStarLoading = state.value.isStarLoading,
                        onStarClick = {
                            viewModel.starUnStarRepo(it.owner.login + "", it.name)
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun RepoDetailScreenPreview() {
    RepoDetailScreen(
        navController = NavController(LocalContext.current),
        username = "test",
        repo = "repo",
    )
}










