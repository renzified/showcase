package com.sleepyhead.showcase.uikit.providers

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sleepyhead.showcase.uikit.R

val GoogleSansFontFamily = FontFamily(
    Font(resId = R.font.google_sans_regular, weight = FontWeight.Normal),
    Font(resId = R.font.google_sans_medium, weight = FontWeight.Medium),
    Font(resId = R.font.google_sans_semibold, weight = FontWeight.SemiBold)
)

val HeadlineRegular = TextStyle(
    fontSize = 17.sp,
    fontFamily = GoogleSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    lineHeight = 100.sp
)

val Regular17 = TextStyle(
    fontSize = 17.sp,
    fontFamily = GoogleSansFontFamily,
    fontWeight = FontWeight.W400,
    lineHeight = 22.sp
)

val Regular16 = TextStyle(
    fontSize = 16.sp,
    fontFamily = GoogleSansFontFamily,
    fontWeight = FontWeight.W400,
    lineHeight = 24.sp,
    letterSpacing = 0.22.sp
)

val MenuLabel = TextStyle(
    fontSize = 14.sp,
    fontFamily = GoogleSansFontFamily,
    fontWeight = FontWeight.W500,
    lineHeight = 20.sp,
    letterSpacing = 0.22.sp
)

val Medium16 = TextStyle(
    fontSize = 16.sp,
    fontFamily = GoogleSansFontFamily,
    fontWeight = FontWeight.W500,
    lineHeight = 24.sp,
    letterSpacing = 0.22.sp
)

val Medium14 = TextStyle(
    fontSize = 14.sp,
    fontFamily = GoogleSansFontFamily,
    fontWeight = FontWeight.W500,
    lineHeight = 20.sp,
    letterSpacing = 0.22.sp
)

val SemiBold22 = TextStyle(
    fontSize = 22.sp,
    fontFamily = GoogleSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    lineHeight = 28.sp,
    letterSpacing = 0.36.sp
)

