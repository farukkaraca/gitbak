package com.farukkaraca.gitbak.presentation.screens.users.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.presentation.components.ProfileImageWithZoom
import com.farukkaraca.gitbak.presentation.state.ProfileState
import com.farukkaraca.gitbak.presentation.state.UserDetailState

@Composable
fun ProfileHeader(
    userDetailState: UserDetailState? = null,
    profileState: ProfileState ? = null,
    onClickFollowButton: (username: String) -> Unit = {}
) {

    val user = if (userDetailState != null) {
        userDetailState.userDetail
    } else {
        profileState?.userDetail
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImageWithZoom(user)

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = user?.name ?: (user?.login + ""),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )

        if (!user?.name.isNullOrBlank()) {
            Text(
                text = user?.login + "",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        userDetailState?.let {
            if (userDetailState.isLoggedIn) {
                FollowButton(
                    isLoading = userDetailState.isLoadingFollowUser,
                    isFollowing = userDetailState.isFollowingUser,
                ) {
                    userDetailState.userDetail?.login?.let(onClickFollowButton)
                }

                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}