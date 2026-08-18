package com.sleepyhead.patterns.mvvm.presentation.watchlist

import com.sleepyhead.patterns.mvvm.data.model.Instrument

data class WatchlistUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val instruments: List<Instrument> = emptyList(),
    val errorMessage: String? = null,
)

sealed interface WatchlistIntent {
    data object Load : WatchlistIntent
    data object Refresh : WatchlistIntent
    data class OpenDetails(val instrumentId: String) : WatchlistIntent
    data class ToggleFavorite(val instrumentId: String) : WatchlistIntent
}

sealed interface WatchlistEffect {
    data class NavigateToDetails(val instrumentId: String) : WatchlistEffect
    data class ShowSnackbar(val message: String) : WatchlistEffect
}
