package com.sleepyhead.patterns.mvi.presentation

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.sleepyhead.patterns.mvi.MviApp
import com.sleepyhead.patterns.mvi.presentation.detail.DetailStore
import com.sleepyhead.patterns.mvi.presentation.watchlist.WatchlistStore

fun Context.appContainer() = (applicationContext as MviApp).container

fun Context.watchlistStoreFactory(): ViewModelProvider.Factory {
    val container = appContainer()
    return WatchlistStore.Factory(
        instrumentRepository = container.instrumentRepository,
        favoritesRepository = container.favoritesRepository,
    )
}

fun Context.detailStoreFactory(instrumentId: String): ViewModelProvider.Factory {
    val container = appContainer()
    return DetailStore.Factory(
        instrumentId = instrumentId,
        instrumentRepository = container.instrumentRepository,
        favoritesRepository = container.favoritesRepository,
    )
}
