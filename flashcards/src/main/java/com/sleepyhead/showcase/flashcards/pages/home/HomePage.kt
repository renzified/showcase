package com.sleepyhead.showcase.flashcards.pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Composable
fun HomePage() {
    Column {
        BasicText(text = "Hello Pre")
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    HomePage()
}