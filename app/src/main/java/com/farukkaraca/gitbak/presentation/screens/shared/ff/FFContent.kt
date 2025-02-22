package com.farukkaraca.gitbak.presentation.screens.shared.ff

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.presentation.components.InfoText
import com.farukkaraca.gitbak.presentation.components.LoadingAnimation
import com.farukkaraca.gitbak.presentation.screens.users.components.UserCard
import com.farukkaraca.gitbak.presentation.state.UserFFState


@Composable
fun FFContent(
    state: UserFFState,
    padding: PaddingValues,
    onClickUser: (User) -> Unit
) {
    val lazyPagingItems = state.pagingData.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        when {
            lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }

            lazyPagingItems.loadState.refresh is LoadState.Error -> {
                val error = (lazyPagingItems.loadState.refresh as LoadState.Error).error
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    InfoText(
                        text = "Error: ${error.message}",
                        showIcon = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { lazyPagingItems.refresh() }) {
                        Text(text = "Retry")
                    }
                }
            }

            lazyPagingItems.itemCount == 0 -> {
                InfoText(
                    text = "No users found",
                    showIcon = true
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = lazyPagingItems.itemCount,
                        key = lazyPagingItems.itemKey { it.id }
                    ) { index ->
                        val user = lazyPagingItems[index]
                        if (user != null) {
                            UserCard(user) {
                                onClickUser(it)
                            }
                        }
                    }

                    when (val appendState = lazyPagingItems.loadState.append) {
                        is LoadState.Loading -> {
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

                        is LoadState.Error -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    InfoText(
                                        text = "Error: ${appendState.error.message}",
                                        showIcon = true
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(onClick = { lazyPagingItems.retry() }) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}