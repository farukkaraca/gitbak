package com.farukkaraca.gitbak.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farukkaraca.gitbak.data.model.UserDetail

@Composable
fun ProfileImageWithZoom(user: UserDetail?) {
    val context = LocalContext.current
    var isZoomed by remember { mutableStateOf(true) }

    Box(contentAlignment = Alignment.Center) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(user?.avatar_url)
                .crossfade(true)
                .build(),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    CircleShape
                )
                .clickable { isZoomed = true },
            contentScale = ContentScale.Crop
        )

        if (isZoomed) {
            Dialog(
                onDismissRequest = { isZoomed = false },
                properties = DialogProperties(
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true,
                    decorFitsSystemWindows = false,
                    securePolicy = SecureFlagPolicy.SecureOff,
                    usePlatformDefaultWidth = false,
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.background.copy(
                                alpha = 0.8f
                            )
                        )
                        .clickable { isZoomed = false },
                    contentAlignment = Alignment.Center
                ) {
                    val size by animateDpAsState(
                        targetValue = if (isZoomed) 300.dp else 120.dp,
                        animationSpec = tween(durationMillis = 300)
                    )

                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(user?.avatar_url)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Zoomed Profile Picture",
                        modifier = Modifier
                            .size(size)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
