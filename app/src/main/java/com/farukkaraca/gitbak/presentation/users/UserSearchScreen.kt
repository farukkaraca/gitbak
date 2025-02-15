package com.farukkaraca.gitbak.presentation.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.farukkaraca.gitbak.presentation.components.CustomTopAppBar
import com.farukkaraca.gitbak.presentation.components.InfoText
import com.farukkaraca.gitbak.presentation.components.LoadingAnimation
import com.farukkaraca.gitbak.presentation.users.components.SearchTextField
import com.farukkaraca.gitbak.presentation.users.components.UserCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSearchScreen(
    isLoading: Boolean = false,
    users: List<User>? = null,
    onSearch: (q: String) -> Unit,
    resetSearch: () -> Unit,
    onClickUser: (User) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "User Search",
                showBackButton = false
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
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

            Spacer(modifier = Modifier.height(4.dp))

            if (isLoading && query.isNotBlank()) {
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
                    users?.let {
                        if (it.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(users) { user ->
                                    UserCard(user) {
                                        onClickUser(user)
                                    }
                                }
                            }
                        } else {
                            if (!isLoading && query.isNotBlank()) {
                                InfoText("User not found", true)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun UserSearchScreenPreview() {
    UserSearchScreen(
        users = listOf(
            User(
                login = "User 1",
                id = 1,
                avatar_url = "",
            ),
        ),
        onSearch = {},
        onClickUser = {},
        resetSearch = {}
    )
}
