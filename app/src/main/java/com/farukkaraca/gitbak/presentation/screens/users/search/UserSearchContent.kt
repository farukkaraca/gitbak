package com.farukkaraca.gitbak.presentation.screens.users.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.presentation.components.InfoText
import com.farukkaraca.gitbak.presentation.components.LoadingAnimation
import com.farukkaraca.gitbak.presentation.screens.users.components.SearchTextField
import com.farukkaraca.gitbak.presentation.screens.users.components.UserCard
import com.farukkaraca.gitbak.presentation.state.UserSearchState

@Composable
fun UserSearchContent(
    padding: PaddingValues,
    state: UserSearchState,
    onSearch: (q: String) -> Unit,
    resetSearch: () -> Unit,
    onClickUser: (User) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 16.dp)
    ) {

        SearchTextField(
            query = query,
            onValueChange = {
                query = it
            },
            onSearch = {
                onSearch(it)
            },
            resetSearch = resetSearch
        )

        if (state.isLoading && query.isNotBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation()
            }
        } else {
            if (query.isBlank()) {
                InfoText("Type to start search...")
            } else {
                state.userSearchResponse?.items?.let {
                    if (it.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.userSearchResponse.items) { user ->
                                UserCard(user) {
                                    onClickUser(user)
                                }
                            }
                        }
                    } else {
                        if (!state.isLoading && query.isNotBlank()) {
                            InfoText("User not found", true)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun UserSearchContentPreview() {
    UserSearchContent(
        padding = PaddingValues(),
        state = UserSearchState(),
        onSearch = {},
        onClickUser = {},
        resetSearch = {}
    )
}
