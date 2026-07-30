package com.sleepyhead.showcase.uikit.providers

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sleepyhead.showcase.uikit.R

val GoogleSansFontFamily = FontFamily(
    Font(resId = R.font.google_sans_regular, weight = FontWeight.Normal),
    Font(resId = R.font.google_sans_semibold, weight = FontWeight.SemiBold)
)

val HeadlineRegular = TextStyle(
    fontSize = 17.sp,
    fontFamily = GoogleSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    lineHeight = 100.sp
)