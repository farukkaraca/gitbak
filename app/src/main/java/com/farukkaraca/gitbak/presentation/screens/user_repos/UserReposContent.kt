package com.farukkaraca.gitbak.presentation.screens.user_repos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.presentation.components.InfoText
import com.farukkaraca.gitbak.presentation.components.LoadingAnimation
import com.farukkaraca.gitbak.presentation.screens.user_repos.components.RepoCard
import com.farukkaraca.gitbak.presentation.state.UserReposState

@Composable
fun UserReposContent(
    padding: PaddingValues,
    state: UserReposState,
) {
    Box(modifier = Modifier.padding(padding)) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }

            state.error.isError -> {
                InfoText(
                    text = "Error: ${state.error.message}",
                    showIcon = true
                )
            }

            state.repos.isEmpty() -> {
                InfoText(
                    text = "No repositories found",
                    showIcon = true
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.repos) { repo ->
                        RepoCard(
                            repo = repo
                        )
                    }
                }
            }
        }
    }
}
