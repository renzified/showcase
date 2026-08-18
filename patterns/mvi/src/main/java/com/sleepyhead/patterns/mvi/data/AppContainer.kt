package com.sleepyhead.patterns.mvi.data

import com.sleepyhead.patterns.mvi.data.model.Instrument
import com.sleepyhead.patterns.mvi.data.repository.FavoritesRepository
import com.sleepyhead.patterns.mvi.data.repository.InMemoryFavoritesRepository
import com.sleepyhead.patterns.mvi.data.repository.InMemoryInstrumentRepository
import com.sleepyhead.patterns.mvi.data.repository.InstrumentRepository

interface AppContainer {
    val instrumentRepository: InstrumentRepository
    val favoritesRepository: FavoritesRepository
}

class DefaultAppContainer : AppContainer {
    override val instrumentRepository: InstrumentRepository = InMemoryInstrumentRepository()
    override val favoritesRepository: FavoritesRepository = InMemoryFavoritesRepository()
}

fun Instrument.withFavorite(favoriteIds: Set<String>): Instrument =
    copy(isFavorite = id in favoriteIds)
