package com.sleepyhead.patterns.mvi.presentation.detail

import com.sleepyhead.patterns.mvi.data.model.Instrument

data class DetailState(
    val isLoading: Boolean = false,
    val instrument: Instrument? = null,
    val errorMessage: String? = null,
)

sealed interface DetailIntent {
    data object Load : DetailIntent
    data object ToggleFavorite : DetailIntent
    data object NavigateBack : DetailIntent
    data class FavoritesChanged(val favoriteIds: Set<String>) : DetailIntent
}

sealed interface DetailResult {
    data object Loading : DetailResult
    data class LoadSuccess(val instrument: Instrument) : DetailResult
    data class LoadError(val message: String) : DetailResult
    data class FavoritesSynced(val favoriteIds: Set<String>) : DetailResult
}

sealed interface DetailEffect {
    data object NavigateBack : DetailEffect
    data class ShowSnackbar(val message: String) : DetailEffect
}
