package com.sleepyhead.patterns.mvi.presentation.detail

import com.sleepyhead.patterns.mvi.data.withFavorite

object DetailReducer {
    fun reduce(state: DetailState, result: DetailResult): DetailState = when (result) {
        DetailResult.Loading -> state.copy(isLoading = true, errorMessage = null)
        is DetailResult.LoadSuccess -> state.copy(
            isLoading = false,
            instrument = result.instrument,
            errorMessage = null,
        )
        is DetailResult.LoadError -> state.copy(
            isLoading = false,
            errorMessage = result.message,
        )
        is DetailResult.FavoritesSynced -> state.copy(
            instrument = state.instrument?.withFavorite(result.favoriteIds),
        )
    }
}
