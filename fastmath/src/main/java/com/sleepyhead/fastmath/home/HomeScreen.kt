package com.sleepyhead.fastmath.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.fastmath.R
import com.sleepyhead.fastmath.home.sections.ChallengeSection
import com.sleepyhead.fastmath.home.sections.ModeSection
import com.sleepyhead.fastmath.home.sections.WarmupSection
import com.sleepyhead.showcase.uikit.providers.PixelifySansFontFamily
import com.sleepyhead.showcase.uikit.themes.UiKitTheme
import com.sleepyhead.showcase.uikit.R as UiKitRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = LocalTextStyle.current.copy(fontFamily = PixelifySansFontFamily)
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(UiKitRes.drawable.ic_settings_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = padding.plus(PaddingValues(16.dp))
        ) {
            item(1) {
                WarmupSection()
            }

            item(2) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    ChallengeSection()
                }
            }

            item(3) {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))
                    ModeSection()
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    UiKitTheme {
        HomeScreen()
    }
}