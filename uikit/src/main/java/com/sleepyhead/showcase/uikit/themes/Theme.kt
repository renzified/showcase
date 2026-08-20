package com.sleepyhead.showcase.uikit.themes

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun UiKitTheme(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalPixelifyTypography provides PixelifyTypography) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = UiKitTypography,
            content = content,
        )
    }
}
