package com.sleepyhead.showcase.uikit.themes

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

private val UiKitLightColorScheme = lightColorScheme()

private val UiKitDarkColorScheme = darkColorScheme()

internal fun uiKitColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) UiKitDarkColorScheme else UiKitLightColorScheme
}
