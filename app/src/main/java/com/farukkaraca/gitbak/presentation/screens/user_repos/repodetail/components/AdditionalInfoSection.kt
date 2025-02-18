package com.farukkaraca.gitbak.presentation.screens.user_repos.repodetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Copyright
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.data.model.RepoDetail

@Composable
fun AdditionalInfoSection(repoDetail: RepoDetail) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repoDetail.language?.let {
            InfoRow(Icons.Rounded.Code, "Language", it)
        }
        repoDetail.license?.let {
            InfoRow(Icons.Rounded.Copyright, "License", it.name)
        }
        InfoRow(
            Icons.Rounded.Update,
            "Updated",
            repoDetail.updated_at.substring(0, 10)
        )
        repoDetail.homepage?.let {
            if (it.isNotBlank()) {
                InfoRow(Icons.Rounded.Link, "Homepage", it)
            }
        }
    }
}