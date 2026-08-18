package com.sleepyhead.patterns.mvvm.presentation.detail

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

class DetailViewModel(
    private val instrumentId: String,
    private val instrumentRepository: InstrumentRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState(isLoading = true))
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _effects = Channel<DetailEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        onIntent(DetailIntent.Load)
        viewModelScope.launch {
            favoritesRepository.favoriteIds.collect { favoriteIds ->
                _uiState.update { state ->
                    state.copy(instrument = state.instrument?.withFavorite(favoriteIds))
                }
            }
        }
    }

    fun onIntent(intent: DetailIntent) {
        when (intent) {
            DetailIntent.Load -> load()
            DetailIntent.ToggleFavorite -> toggleFavorite()
            DetailIntent.NavigateBack -> emitEffect(DetailEffect.NavigateBack)
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { instrumentRepository.getInstrument(instrumentId) }
                .onSuccess { instrument ->
                    if (instrument == null) {
                        _uiState.update {
                            it.copy(isLoading = false, errorMessage = "Instrument not found")
                        }
                    } else {
                        val favorites = favoritesRepository.favoriteIds.value
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                instrument = instrument.withFavorite(favorites),
                                errorMessage = null,
                            )
                        }
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Failed to load instrument",
                        )
                    }
                }
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            val instrument = _uiState.value.instrument ?: return@launch
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
            require(modelClass.isAssignableFrom(DetailViewModel::class.java))
            return DetailViewModel(instrumentId, instrumentRepository, favoritesRepository) as T
        }
    }
}
