package com.farukkaraca.gitbak.presentation.screens.users.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.PersonRemove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farukkaraca.gitbak.presentation.components.LoadingAnimation

@Composable
fun FollowButton(
    isFollowing: Boolean,
    isLoading: Boolean = false,
    onFollowClick: () -> Unit,
) {
    Button(
        onClick = { if (!isLoading) onFollowClick() },
        modifier = Modifier.height(40.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFollowing)
                MaterialTheme.colorScheme.surface
            else
                MaterialTheme.colorScheme.primary,
            contentColor = if (isFollowing)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.onPrimary
        ),
        border = if (isFollowing) {
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
        } else null,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                LoadingAnimation(
                    circleColor = if (isFollowing)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Icon(
                imageVector = if (isFollowing)
                    Icons.Rounded.PersonRemove
                else
                    Icons.Rounded.PersonAdd,
                contentDescription = if (isFollowing) "Unfollow" else "Follow",
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = if (isFollowing) "Following" else "Follow",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Preview
@Composable
private fun FollowButtonPreview() {
    FollowButton(
        isFollowing = true,
        isLoading = false,
    ) { }
}