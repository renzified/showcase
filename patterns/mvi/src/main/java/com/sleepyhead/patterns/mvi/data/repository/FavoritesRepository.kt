package com.sleepyhead.patterns.mvi.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface FavoritesRepository {
    val favoriteIds: StateFlow<Set<String>>
    suspend fun addFavorite(instrumentId: String): Boolean
    suspend fun removeFavorite(instrumentId: String): Boolean
}

class InMemoryFavoritesRepository : FavoritesRepository {

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    override val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    override suspend fun addFavorite(instrumentId: String): Boolean {
        if (instrumentId in _favoriteIds.value) return false
        _favoriteIds.update { it + instrumentId }
        return true
    }

    override suspend fun removeFavorite(instrumentId: String): Boolean {
        if (instrumentId !in _favoriteIds.value) return false
        _favoriteIds.update { it - instrumentId }
        return true
    }
}
