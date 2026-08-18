package com.sleepyhead.patterns.mvi.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sleepyhead.patterns.mvi.data.repository.FavoritesRepository
import com.sleepyhead.patterns.mvi.data.repository.InstrumentRepository
import com.sleepyhead.patterns.mvi.data.withFavorite
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MVI Store:
 * Intent → processor (async) → Result → [WatchlistReducer] → State
 *                          ↘ Effect (navigation / snackbar)
 */
class WatchlistStore(
    private val instrumentRepository: InstrumentRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(WatchlistState(isLoading = true))
    val state: StateFlow<WatchlistState> = _state.asStateFlow()

    private val _effects = Channel<WatchlistEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(WatchlistIntent.Load)
        viewModelScope.launch {
            favoritesRepository.favoriteIds.collect { ids ->
                dispatch(WatchlistIntent.FavoritesChanged(ids))
            }
        }
    }

    fun dispatch(intent: WatchlistIntent) {
        when (intent) {
            WatchlistIntent.Load -> load(isRefresh = false)
            WatchlistIntent.Refresh -> load(isRefresh = true)
            is WatchlistIntent.OpenDetails -> emitEffect(
                WatchlistEffect.NavigateToDetails(intent.instrumentId),
            )
            is WatchlistIntent.ToggleFavorite -> toggleFavorite(intent.instrumentId)
            is WatchlistIntent.FavoritesChanged -> reduce(
                WatchlistResult.FavoritesSynced(intent.favoriteIds),
            )
        }
    }

    private fun load(isRefresh: Boolean) {
        viewModelScope.launch {
            reduce(if (isRefresh) WatchlistResult.Refreshing else WatchlistResult.Loading)
            runCatching { instrumentRepository.getWatchlist() }
                .onSuccess { list ->
                    val favorites = favoritesRepository.favoriteIds.value
                    reduce(
                        WatchlistResult.LoadSuccess(
                            instruments = list.map { it.withFavorite(favorites) },
                        ),
                    )
                }
                .onFailure { error ->
                    reduce(
                        WatchlistResult.LoadError(
                            message = error.message ?: "Failed to load watchlist",
                        ),
                    )
                }
        }
    }

    private fun toggleFavorite(instrumentId: String) {
        viewModelScope.launch {
            val instrument = _state.value.instruments.find { it.id == instrumentId } ?: return@launch
            val added = if (instrument.isFavorite) {
                favoritesRepository.removeFavorite(instrumentId)
                false
            } else {
                favoritesRepository.addFavorite(instrumentId)
            }
            emitEffect(
                WatchlistEffect.ShowSnackbar(
                    if (added) {
                        "${instrument.symbol} added to favorites"
                    } else {
                        "${instrument.symbol} removed from favorites"
                    },
                ),
            )
        }
    }

    private fun reduce(result: WatchlistResult) {
        _state.update { WatchlistReducer.reduce(it, result) }
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
            require(modelClass.isAssignableFrom(WatchlistStore::class.java))
            return WatchlistStore(instrumentRepository, favoritesRepository) as T
        }
    }
}
