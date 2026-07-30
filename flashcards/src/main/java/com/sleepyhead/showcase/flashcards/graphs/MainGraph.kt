package com.sleepyhead.showcase.flashcards.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sleepyhead.showcase.flashcards.pages.home.HomeDestination
import com.sleepyhead.showcase.flashcards.pages.home.HomePage

@Composable
fun MainGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeDestination) {
        composable<HomeDestination> { HomePage() }
    }
}