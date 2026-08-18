package com.sleepyhead.patterns.mvi.presentation.watchlist

import com.sleepyhead.patterns.mvi.data.model.Instrument

/** Single immutable Model for the Watchlist feature. */
data class WatchlistState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val instruments: List<Instrument> = emptyList(),
    val errorMessage: String? = null,
)

/** User / system Intents — the only input into the store. */
sealed interface WatchlistIntent {
    data object Load : WatchlistIntent
    data object Refresh : WatchlistIntent
    data class OpenDetails(val instrumentId: String) : WatchlistIntent
    data class ToggleFavorite(val instrumentId: String) : WatchlistIntent
    data class FavoritesChanged(val favoriteIds: Set<String>) : WatchlistIntent
}

/**
 * Partial results produced by intent processors.
 * Pure [WatchlistReducer] folds these into [WatchlistState].
 */
sealed interface WatchlistResult {
    data object Loading : WatchlistResult
    data object Refreshing : WatchlistResult
    data class LoadSuccess(val instruments: List<Instrument>) : WatchlistResult
    data class LoadError(val message: String) : WatchlistResult
    data class FavoritesSynced(val favoriteIds: Set<String>) : WatchlistResult
}

/** One-shot side effects — never stored in state. */
sealed interface WatchlistEffect {
    data class NavigateToDetails(val instrumentId: String) : WatchlistEffect
    data class ShowSnackbar(val message: String) : WatchlistEffect
}
