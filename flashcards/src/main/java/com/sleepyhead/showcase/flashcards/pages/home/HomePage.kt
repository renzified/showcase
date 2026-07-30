package com.sleepyhead.showcase.flashcards.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.appbar.UiKitAppBar
import com.sleepyhead.showcase.uikit.providers.ColorFoundationMain
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Composable
fun HomePage() {
    val topInset = WindowInsets.statusBars.only(WindowInsetsSides.Top)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorFoundationMain)
            .windowInsetsPadding(topInset)
    ) {
        UiKitAppBar()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
        ) {
            items(count = 12) {
                HomeItem()
            }
        }
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    HomePage()
}