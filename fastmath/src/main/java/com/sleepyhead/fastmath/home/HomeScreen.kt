package com.sleepyhead.fastmath.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sleepyhead.fastmath.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(R.string.fast_math))
            })
        }
    ) {
        
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen()
}