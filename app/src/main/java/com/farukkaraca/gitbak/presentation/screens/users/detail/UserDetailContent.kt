package com.farukkaraca.gitbak.presentation.screens.users.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.presentation.components.InfoText
import com.farukkaraca.gitbak.presentation.components.LoadingAnimation
import com.farukkaraca.gitbak.presentation.screens.users.detail.components.ContactInfoSection
import com.farukkaraca.gitbak.presentation.screens.users.detail.components.ProfileHeader
import com.farukkaraca.gitbak.presentation.screens.users.detail.components.ProfileSection
import com.farukkaraca.gitbak.presentation.screens.users.detail.components.StatsSection
import com.farukkaraca.gitbak.presentation.state.UserDetailState

@Composable
fun UserDetailContent(
    state: UserDetailState,
    padding: PaddingValues,
    onClickFollowers: () -> Unit,
    onClickFollowing: () -> Unit,
    onClickRepos: () -> Unit,
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
                state.userDetail?.let { user ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            ProfileHeader(user)
                        }

                        if (!user.bio.isNullOrBlank()) {
                            item {
                                ProfileSection(
                                    icon = Icons.Rounded.Description,
                                    content = user.bio
                                )
                            }
                        }

                        item {
                            StatsSection(
                                user = user,
                                onClickFollowers = {
                                    onClickFollowers()
                                },
                                onClickFollowing = {
                                    onClickFollowing()
                                },
                                onClickRepos = {
                                    onClickRepos()
                                }
                            )
                        }

                        item {
                            ContactInfoSection(user)
                        }
                    }
                }
            }
        }
    }
}





