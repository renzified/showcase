package com.sleepyhead.patterns.mvvm.presentation.common

import java.util.Locale
import kotlin.math.abs

fun formatPrice(price: Double, currency: String = "USD"): String =
    String.format(Locale.US, "%s %.2f", currency, price)

fun formatChangePercent(changePercent: Double): String {
    val sign = if (changePercent >= 0) "+" else "-"
    return String.format(Locale.US, "%s%.2f%%", sign, abs(changePercent))
}
