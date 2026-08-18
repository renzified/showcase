package com.sleepyhead.patterns.mvvm.data.model

data class Instrument(
    val id: String,
    val symbol: String,
    val name: String,
    val price: Double,
    val changePercent: Double,
    val currency: String = "USD",
    val sector: String = "",
    val description: String = "",
    val isFavorite: Boolean = false,
)
