package com.farukkaraca.gitbak.presentation.screens.users.detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.data.model.UserDetail

@Composable
fun StatsSection(user: UserDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        StatItem(
            count = user.followers,
            label = "Followers",
            modifier = Modifier.weight(1f)
        )
        StatItem(
            count = user.following,
            label = "Following",
            modifier = Modifier.weight(1f)
        )
        StatItem(
            count = user.public_repos,
            label = "Repos",
            modifier = Modifier.weight(1f)
        )
    }
}