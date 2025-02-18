package com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.data.model.RepoDetail
import com.farukkaraca.gitbak.presentation.components.LoadingAnimation

@Composable
fun RepoHeader(
    repoDetail: RepoDetail,
    owner: String?,
    isUserLoggedIn: Boolean,
    isStarred: Boolean,
    isStarLoading: Boolean,
    onStarClick: (RepoDetail) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = repoDetail.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = repoDetail.owner.login + "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (isUserLoggedIn && owner != null && owner != repoDetail.owner.login) {
            IconButton(
                onClick = {
                    onStarClick(repoDetail)
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    )
            ) {

                if (isStarLoading) {
                    LoadingAnimation()
                } else {
                    Icon(
                        imageVector = if (isStarred)
                            Icons.Filled.Star
                        else
                            Icons.Outlined.Star,
                        contentDescription = if (isStarred) "Unstar" else "Star",
                        tint = if (isStarred)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}