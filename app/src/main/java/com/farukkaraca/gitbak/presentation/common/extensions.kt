package com.farukkaraca.gitbak.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun PaddingValues.toBottomlessPadding(): PaddingValues {
    return PaddingValues(
        start = calculateStartPadding(LocalLayoutDirection.current),
        top = calculateTopPadding(),
        end = calculateEndPadding(LocalLayoutDirection.current),
        bottom = 2.dp
    )
}