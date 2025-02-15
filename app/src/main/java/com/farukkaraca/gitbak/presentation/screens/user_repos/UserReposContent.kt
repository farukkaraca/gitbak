package com.farukkaraca.gitbak.presentation.screens.user_repos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
    onScroll: (page: Int) -> Unit
) {
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
    ) {
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
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.repos) { repo ->
                        RepoCard(
                            repo = repo
                        )
                    }

                    itemsIndexed(state.repos) { index, repo ->
                        RepoCard(
                            repo = repo
                        )

                        if (index == state.repos.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                            onScroll(state.page + 1)
                        }
                    }
                }
            }
        }
    }
}
