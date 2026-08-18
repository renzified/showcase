package com.sleepyhead.patterns.mvi.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sleepyhead.patterns.mvi.compose.detail.DetailScreen
import com.sleepyhead.patterns.mvi.compose.watchlist.WatchlistScreen
import com.sleepyhead.patterns.mvi.presentation.detail.DetailStore
import com.sleepyhead.patterns.mvi.presentation.detailStoreFactory
import com.sleepyhead.patterns.mvi.presentation.watchlist.WatchlistStore
import com.sleepyhead.patterns.mvi.presentation.watchlistStoreFactory

object ComposeRoutes {
    const val WATCHLIST = "watchlist"
    const val DETAIL = "detail/{instrumentId}"
    fun detail(instrumentId: String) = "detail/$instrumentId"
}

@Composable
fun ComposeMviNavHost(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = ComposeRoutes.WATCHLIST,
        modifier = modifier,
    ) {
        composable(ComposeRoutes.WATCHLIST) {
            val store: WatchlistStore = viewModel(factory = context.watchlistStoreFactory())
            WatchlistScreen(
                store = store,
                onNavigateToDetails = { id ->
                    navController.navigate(ComposeRoutes.detail(id))
                },
                onBack = onClose,
            )
        }
        composable(
            route = ComposeRoutes.DETAIL,
            arguments = listOf(navArgument("instrumentId") { type = NavType.StringType }),
        ) { entry ->
            val instrumentId = entry.arguments?.getString("instrumentId").orEmpty()
            val store: DetailStore = viewModel(
                factory = context.detailStoreFactory(instrumentId),
            )
            DetailScreen(
                store = store,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
