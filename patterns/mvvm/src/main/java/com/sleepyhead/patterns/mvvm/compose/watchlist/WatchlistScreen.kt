package com.sleepyhead.patterns.mvvm.compose.watchlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sleepyhead.patterns.mvvm.data.model.Instrument
import com.sleepyhead.patterns.mvvm.presentation.common.formatChangePercent
import com.sleepyhead.patterns.mvvm.presentation.common.formatPrice
import com.sleepyhead.patterns.mvvm.presentation.watchlist.WatchlistEffect
import com.sleepyhead.patterns.mvvm.presentation.watchlist.WatchlistIntent
import com.sleepyhead.patterns.mvvm.presentation.watchlist.WatchlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    viewModel: WatchlistViewModel,
    onNavigateToDetails: (String) -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is WatchlistEffect.NavigateToDetails -> onNavigateToDetails(effect.instrumentId)
                is WatchlistEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Watchlist (Compose)") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null && state.instruments.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(state.errorMessage.orEmpty())
                }
            }

            else -> {
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    onRefresh = { viewModel.onIntent(WatchlistIntent.Refresh) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp),
                    ) {
                        items(state.instruments, key = { it.id }) { instrument ->
                            InstrumentRow(
                                instrument = instrument,
                                onClick = {
                                    viewModel.onIntent(WatchlistIntent.OpenDetails(instrument.id))
                                },
                                onFavoriteClick = {
                                    viewModel.onIntent(WatchlistIntent.ToggleFavorite(instrument.id))
                                },
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InstrumentRow(
    instrument: Instrument,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    val changeColor = if (instrument.changePercent >= 0) {
        Color(0xFF1B7F4E)
    } else {
        Color(0xFFB3261E)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = instrument.symbol,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = instrument.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = formatPrice(instrument.price, instrument.currency),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = formatChangePercent(instrument.changePercent),
                style = MaterialTheme.typography.bodyMedium,
                color = changeColor,
            )
        }
        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (instrument.isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                },
                contentDescription = if (instrument.isFavorite) {
                    "Remove from favorites"
                } else {
                    "Add to favorites"
                },
                tint = if (instrument.isFavorite) {
                    Color(0xFFB3261E)
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            )
        }
    }
}
