package com.sleepyhead.patterns.mvvm.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sleepyhead.patterns.mvvm.data.repository.FavoritesRepository
import com.sleepyhead.patterns.mvvm.data.repository.InstrumentRepository
import com.sleepyhead.patterns.mvvm.data.withFavorite
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Strict MVVM ViewModel:
 * - Holds immutable [WatchlistUiState] only
 * - Accepts [WatchlistIntent] from the View
 * - Emits one-shot [WatchlistEffect] for snackbar / navigation
 * - Never references Android Views / Context
 */
class WatchlistViewModel(
    private val instrumentRepository: InstrumentRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WatchlistUiState(isLoading = true))
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    private val _effects = Channel<WatchlistEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        onIntent(WatchlistIntent.Load)
        viewModelScope.launch {
            favoritesRepository.favoriteIds.collect { favoriteIds ->
                _uiState.update { state ->
                    state.copy(
                        instruments = state.instruments.map { it.withFavorite(favoriteIds) },
                    )
                }
            }
        }
    }

    fun onIntent(intent: WatchlistIntent) {
        when (intent) {
            WatchlistIntent.Load -> load(isRefresh = false)
            WatchlistIntent.Refresh -> load(isRefresh = true)
            is WatchlistIntent.OpenDetails -> emitEffect(
                WatchlistEffect.NavigateToDetails(intent.instrumentId),
            )
            is WatchlistIntent.ToggleFavorite -> toggleFavorite(intent.instrumentId)
        }
    }

    private fun load(isRefresh: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = !isRefresh && it.instruments.isEmpty(),
                    isRefreshing = isRefresh,
                    errorMessage = null,
                )
            }
            runCatching { instrumentRepository.getWatchlist() }
                .onSuccess { list ->
                    val favorites = favoritesRepository.favoriteIds.value
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            instruments = list.map { instrument -> instrument.withFavorite(favorites) },
                            errorMessage = null,
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = error.message ?: "Failed to load watchlist",
                        )
                    }
                }
        }
    }

    private fun toggleFavorite(instrumentId: String) {
        viewModelScope.launch {
            val instrument = _uiState.value.instruments.find { it.id == instrumentId } ?: return@launch
            val added = if (instrument.isFavorite) {
                favoritesRepository.removeFavorite(instrumentId)
                false
            } else {
                favoritesRepository.addFavorite(instrumentId)
            }
            val symbol = instrument.symbol
            emitEffect(
                WatchlistEffect.ShowSnackbar(
                    if (added) "$symbol added to favorites" else "$symbol removed from favorites",
                ),
            )
        }
    }

    private fun emitEffect(effect: WatchlistEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }

    class Factory(
        private val instrumentRepository: InstrumentRepository,
        private val favoritesRepository: FavoritesRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(WatchlistViewModel::class.java))
            return WatchlistViewModel(instrumentRepository, favoritesRepository) as T
        }
    }
}
