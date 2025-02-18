package com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.data.model.RepoDetail
import com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail.components.AdditionalInfoSection
import com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail.components.RepoHeader
import com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail.components.StatsSection
import com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail.components.TopicsSection

@Composable
fun RepoDetailContent(
    repoDetail: RepoDetail,
    isUserLoggedIn: Boolean,
    owner: String?,
    isStarred: Boolean,
    isStarLoading: Boolean,
    onStarClick: (RepoDetail) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            RepoHeader(
                repoDetail = repoDetail,
                owner = owner,
                isUserLoggedIn = isUserLoggedIn,
                isStarred = isStarred,
                isStarLoading = isStarLoading,
                onStarClick = {onStarClick(repoDetail)}
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        if (!repoDetail.description.isNullOrBlank()) {
            item {
                Text(
                    text = repoDetail.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            StatsSection(repoDetail)
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (repoDetail.topics.isNotEmpty()) {
            item {
                TopicsSection(repoDetail.topics)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            AdditionalInfoSection(repoDetail)
        }
    }
}
