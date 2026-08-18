package com.sleepyhead.patterns.mvvm.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sleepyhead.patterns.mvvm.compose.detail.DetailScreen
import com.sleepyhead.patterns.mvvm.compose.watchlist.WatchlistScreen
import com.sleepyhead.patterns.mvvm.presentation.detail.DetailViewModel
import com.sleepyhead.patterns.mvvm.presentation.detailViewModelFactory
import com.sleepyhead.patterns.mvvm.presentation.watchlist.WatchlistViewModel
import com.sleepyhead.patterns.mvvm.presentation.watchlistViewModelFactory
import androidx.compose.ui.platform.LocalContext

object ComposeRoutes {
    const val WATCHLIST = "watchlist"
    const val DETAIL = "detail/{instrumentId}"
    fun detail(instrumentId: String) = "detail/$instrumentId"
}

@Composable
fun ComposeMvvmNavHost(
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
            val viewModel: WatchlistViewModel = viewModel(
                factory = context.watchlistViewModelFactory(),
            )
            WatchlistScreen(
                viewModel = viewModel,
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
            val viewModel: DetailViewModel = viewModel(
                factory = context.detailViewModelFactory(instrumentId),
            )
            DetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
