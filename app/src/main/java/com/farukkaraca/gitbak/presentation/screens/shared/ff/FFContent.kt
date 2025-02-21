package com.farukkaraca.gitbak.presentation.screens.shared.ff

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.presentation.components.InfoText
import com.farukkaraca.gitbak.presentation.components.LoadingAnimation
import com.farukkaraca.gitbak.presentation.screens.users.components.UserCard
import com.farukkaraca.gitbak.presentation.state.UserFFState
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun FFContent(
    state: UserFFState,
    padding: PaddingValues,
    onScroll: (page: Int) -> Unit,
    onClickUser: (User) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState, state.users) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastIndex ->
                if (lastIndex == state.users.lastIndex && !state.isScroll) {
                    onScroll(state.page + 1)
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
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

            state.users.isEmpty() -> {
                InfoText(
                    text = "No users found",
                    showIcon = true
                )
            }

            else -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    itemsIndexed(state.users) { index, user ->
                        UserCard(
                            user
                        ) {
                            onClickUser(it)
                        }
                    }

                    if (state.isScroll) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}