package com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ForkRight
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.data.model.RepoDetail

@Composable
fun StatsSection(repoDetail: RepoDetail) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard(
            icon = Icons.Rounded.Star,
            value = repoDetail.stargazers_count,
            label = "Stars",
            modifier = Modifier.weight(1f)
        )
        StatCard(
            icon = Icons.Rounded.ForkRight,
            value = repoDetail.forks_count,
            label = "Forks",
            modifier = Modifier.weight(1f)
        )
        StatCard(
            icon = Icons.Rounded.RemoveRedEye,
            value = repoDetail.watchers_count,
            label = "Watchers",
            modifier = Modifier.weight(1f)
        )
    }
}