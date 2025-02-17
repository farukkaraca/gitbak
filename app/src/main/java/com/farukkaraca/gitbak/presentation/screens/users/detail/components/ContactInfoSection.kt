package com.farukkaraca.gitbak.presentation.screens.users.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Tag
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.data.model.UserDetail

@Composable
fun ContactInfoSection(user: UserDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        user.company?.let {
            ProfileSection(Icons.Rounded.Business, it)
        }
        user.location?.let {
            ProfileSection(Icons.Rounded.LocationOn, it)
        }
        user.email?.let {
            ProfileSection(Icons.Rounded.Email, it)
        }
        user.blog?.let {
            if (it.isNotEmpty()) {
                ProfileSection(Icons.Rounded.Link, it)
            }
        }
        user.twitter_username?.let {
            ProfileSection(Icons.Rounded.Tag, "@$it")
        }
    }
}