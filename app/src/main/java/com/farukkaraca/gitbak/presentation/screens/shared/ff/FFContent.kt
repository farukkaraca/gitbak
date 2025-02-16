package com.farukkaraca.gitbak.presentation.screens.shared.ff

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

            else -> {
                if (state.users.isNotEmpty()) {
                    LazyColumn {
                        itemsIndexed(state.users) { index, user ->
                            UserCard(
                                user
                            ) {
                                onClickUser(it)
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        InfoText("There is nothing to display")
                    }
                }
            }
        }
    }
}