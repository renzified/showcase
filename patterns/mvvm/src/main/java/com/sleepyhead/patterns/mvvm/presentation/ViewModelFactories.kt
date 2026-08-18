package com.sleepyhead.patterns.mvvm.presentation

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.sleepyhead.patterns.mvvm.MvvmApp
import com.sleepyhead.patterns.mvvm.presentation.detail.DetailViewModel
import com.sleepyhead.patterns.mvvm.presentation.watchlist.WatchlistViewModel

fun Context.appContainer() = (applicationContext as MvvmApp).container

fun Context.watchlistViewModelFactory(): ViewModelProvider.Factory {
    val container = appContainer()
    return WatchlistViewModel.Factory(
        instrumentRepository = container.instrumentRepository,
        favoritesRepository = container.favoritesRepository,
    )
}

fun Context.detailViewModelFactory(instrumentId: String): ViewModelProvider.Factory {
    val container = appContainer()
    return DetailViewModel.Factory(
        instrumentId = instrumentId,
        instrumentRepository = container.instrumentRepository,
        favoritesRepository = container.favoritesRepository,
    )
}
