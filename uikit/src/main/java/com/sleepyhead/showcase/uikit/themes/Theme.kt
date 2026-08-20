package com.sleepyhead.showcase.uikit.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun UiKitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = uiKitColorScheme(darkTheme)
    CompositionLocalProvider(LocalPixelifyTypography provides PixelifyTypography) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = UiKitTypography,
            content = content,
        )
    }
}
