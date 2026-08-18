package com.sleepyhead.patterns.mvi.presentation.detail

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

class DetailStore(
    private val instrumentId: String,
    private val instrumentRepository: InstrumentRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState(isLoading = true))
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val _effects = Channel<DetailEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        dispatch(DetailIntent.Load)
        viewModelScope.launch {
            favoritesRepository.favoriteIds.collect { ids ->
                dispatch(DetailIntent.FavoritesChanged(ids))
            }
        }
    }

    fun dispatch(intent: DetailIntent) {
        when (intent) {
            DetailIntent.Load -> load()
            DetailIntent.ToggleFavorite -> toggleFavorite()
            DetailIntent.NavigateBack -> emitEffect(DetailEffect.NavigateBack)
            is DetailIntent.FavoritesChanged -> reduce(
                DetailResult.FavoritesSynced(intent.favoriteIds),
            )
        }
    }

    private fun load() {
        viewModelScope.launch {
            reduce(DetailResult.Loading)
            runCatching { instrumentRepository.getInstrument(instrumentId) }
                .onSuccess { instrument ->
                    if (instrument == null) {
                        reduce(DetailResult.LoadError("Instrument not found"))
                    } else {
                        val favorites = favoritesRepository.favoriteIds.value
                        reduce(DetailResult.LoadSuccess(instrument.withFavorite(favorites)))
                    }
                }
                .onFailure { error ->
                    reduce(
                        DetailResult.LoadError(
                            message = error.message ?: "Failed to load instrument",
                        ),
                    )
                }
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            val instrument = _state.value.instrument ?: return@launch
            val added = if (instrument.isFavorite) {
                favoritesRepository.removeFavorite(instrument.id)
                false
            } else {
                favoritesRepository.addFavorite(instrument.id)
            }
            emitEffect(
                DetailEffect.ShowSnackbar(
                    if (added) {
                        "${instrument.symbol} added to favorites"
                    } else {
                        "${instrument.symbol} removed from favorites"
                    },
                ),
            )
        }
    }

    private fun reduce(result: DetailResult) {
        _state.update { DetailReducer.reduce(it, result) }
    }

    private fun emitEffect(effect: DetailEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }

    class Factory(
        private val instrumentId: String,
        private val instrumentRepository: InstrumentRepository,
        private val favoritesRepository: FavoritesRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(DetailStore::class.java))
            return DetailStore(instrumentId, instrumentRepository, favoritesRepository) as T
        }
    }
}
