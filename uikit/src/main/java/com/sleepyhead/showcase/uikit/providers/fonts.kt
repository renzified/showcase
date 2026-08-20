package com.sleepyhead.showcase.uikit.providers

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.sleepyhead.showcase.uikit.R

val GoogleSansFontFamily = FontFamily(
    Font(resId = R.font.google_sans_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(resId = R.font.google_sans_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(resId = R.font.google_sans_semibold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(resId = R.font.google_sans_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(resId = R.font.google_sans_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(resId = R.font.google_sans_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(resId = R.font.google_sans_semibold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(resId = R.font.google_sans_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
)

val PixelifySansFontFamily = FontFamily(
    Font(resId = R.font.pixelify_sans_regular, weight = FontWeight.Normal),
    Font(resId = R.font.pixelify_sans_medium, weight = FontWeight.Medium),
    Font(resId = R.font.pixelify_sans_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.pixelify_sans_bold, weight = FontWeight.Bold),
)
