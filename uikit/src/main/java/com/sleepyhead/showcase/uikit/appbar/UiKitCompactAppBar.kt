package com.sleepyhead.showcase.uikit.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.R
import com.sleepyhead.showcase.uikit.providers.ColorFoundationMain
import com.sleepyhead.showcase.uikit.providers.HeadlineRegular
import com.sleepyhead.showcase.uikit.search.UiKitSearch

@Composable
fun UiKitCompactAppBar(
    modifier: Modifier = Modifier,
    navigationButton: (@Composable BoxScope.() -> Unit)? = null,
    title: String? = null,
    secondRow: (@Composable () -> Unit)? = null
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.height(54.dp)) {
            val boxScope = this

            navigationButton?.let {
                Row(modifier = Modifier.align(Alignment.CenterStart)) {
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    it.invoke(boxScope)
                }
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                if (!title.isNullOrBlank()) {
                    BasicText(
                        text = title,
                        style = HeadlineRegular
                    )
                }
            }
        }

        if (secondRow != null) secondRow()
    }
}

@Preview
@Composable
private fun PreviewUiKitCompactAppBarAppBar() {
    Column(
        modifier = Modifier.background(ColorFoundationMain)
    ) {
        UiKitCompactAppBar(
            navigationButton = {
                NavigationButton(
                    modifier = Modifier,
                    icon = R.drawable.ic_navigation_back
                )
            },
            title = "Apps"
        ) {
            UiKitSearch(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 10.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewUiKitCompactAppBarAppBarNoNavigation() {
    Column(
        modifier = Modifier.background(ColorFoundationMain)
    ) {
        UiKitCompactAppBar(
            title = "Apps"
        ) {
            UiKitSearch(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 10.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewUiKitCompactAppBarAppBarNoSecondRow() {
    Column(
        modifier = Modifier.background(ColorFoundationMain)
    ) {
        UiKitCompactAppBar(title = "Apps")
    }
}