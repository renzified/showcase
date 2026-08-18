package com.sleepyhead.patterns.mvvm.compose.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.sleepyhead.patterns.mvvm.presentation.common.formatChangePercent
import com.sleepyhead.patterns.mvvm.presentation.common.formatPrice
import com.sleepyhead.patterns.mvvm.presentation.detail.DetailEffect
import com.sleepyhead.patterns.mvvm.presentation.detail.DetailIntent
import com.sleepyhead.patterns.mvvm.presentation.detail.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val instrument = state.instrument

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                DetailEffect.NavigateBack -> onBack()
                is DetailEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(instrument?.symbol ?: "Details") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onIntent(DetailIntent.NavigateBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (instrument != null) {
                        IconButton(onClick = { viewModel.onIntent(DetailIntent.ToggleFavorite) }) {
                            Icon(
                                imageVector = if (instrument.isFavorite) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Filled.FavoriteBorder
                                },
                                contentDescription = "Favorite",
                                tint = if (instrument.isFavorite) {
                                    Color(0xFFB3261E)
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                },
                            )
                        }
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

            state.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(state.errorMessage.orEmpty())
                }
            }

            instrument != null -> {
                val changeColor = if (instrument.changePercent >= 0) {
                    Color(0xFF1B7F4E)
                } else {
                    Color(0xFFB3261E)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = instrument.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = formatPrice(instrument.price, instrument.currency),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        text = formatChangePercent(instrument.changePercent),
                        style = MaterialTheme.typography.titleMedium,
                        color = changeColor,
                    )
                    Text(
                        text = "Sector: ${instrument.sector}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = instrument.description,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Button(
                        onClick = { viewModel.onIntent(DetailIntent.ToggleFavorite) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Text(
                            if (instrument.isFavorite) "Remove from favorites" else "Add to favorites",
                        )
                    }
                }
            }
        }
    }
}
