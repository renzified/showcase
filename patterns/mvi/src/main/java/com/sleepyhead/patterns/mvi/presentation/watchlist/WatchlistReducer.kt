package com.sleepyhead.patterns.mvi.presentation.watchlist

import com.sleepyhead.patterns.mvi.data.withFavorite

/**
 * Pure reducer: (State, Result) -> State.
 * No IO, no coroutines, no Android — easy to unit test.
 */
object WatchlistReducer {
    fun reduce(state: WatchlistState, result: WatchlistResult): WatchlistState = when (result) {
        WatchlistResult.Loading -> state.copy(
            isLoading = state.instruments.isEmpty(),
            isRefreshing = false,
            errorMessage = null,
        )
        WatchlistResult.Refreshing -> state.copy(
            isRefreshing = true,
            errorMessage = null,
        )
        is WatchlistResult.LoadSuccess -> state.copy(
            isLoading = false,
            isRefreshing = false,
            instruments = result.instruments,
            errorMessage = null,
        )
        is WatchlistResult.LoadError -> state.copy(
            isLoading = false,
            isRefreshing = false,
            errorMessage = result.message,
        )
        is WatchlistResult.FavoritesSynced -> state.copy(
            instruments = state.instruments.map { it.withFavorite(result.favoriteIds) },
        )
    }
}
