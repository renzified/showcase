package com.sleepyhead.patterns.mvvm.presentation.detail

import com.sleepyhead.patterns.mvvm.data.model.Instrument

data class DetailUiState(
    val isLoading: Boolean = false,
    val instrument: Instrument? = null,
    val errorMessage: String? = null,
)

sealed interface DetailIntent {
    data object Load : DetailIntent
    data object ToggleFavorite : DetailIntent
    data object NavigateBack : DetailIntent
}

sealed interface DetailEffect {
    data object NavigateBack : DetailEffect
    data class ShowSnackbar(val message: String) : DetailEffect
}
