package com.sleepyhead.showcase.uikit.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.sleepyhead.showcase.uikit.providers.SemiBold22
import com.sleepyhead.showcase.uikit.search.UiKitSearch

@Composable
fun UiKitAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    navigationButton: (@Composable () -> Unit)? = null,
    secondRow: (@Composable ColumnScope.() -> Unit)? = null
) {
    Box(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .height(54.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                navigationButton?.let {
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    it.invoke()
                }

                if (!title.isNullOrBlank()) {
                    BasicText(
                        text = title,
                        style = SemiBold22,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                }
            }

            if (secondRow != null) secondRow()
        }
    }
}

@Preview
@Composable
private fun PreviewUiKitAppBarAppBar() {
    Box(modifier = Modifier.background(ColorFoundationMain)) {
        UiKitAppBar(
            navigationButton = {
                NavigationButton(icon = R.drawable.ic_close, modifier = Modifier)
            },
            title = "App"
        ) {
            UiKitSearch(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}
