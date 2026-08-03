package com.sleepyhead.showcase.uikit.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.R
import com.sleepyhead.showcase.uikit.appbar.NavigationButton
import com.sleepyhead.showcase.uikit.appbar.UiKitCompactAppBar
import com.sleepyhead.showcase.uikit.providers.ColorFoundationMain

@Composable
fun UiKitBottomSheet() {
    val menus = remember {
        listOf(
            BottomSheetMenu(
                id = 1,
                label = "Create with AI",
                value = null
            ),
            BottomSheetMenu(
                id = 2,
                label = "Create manually",
                value = null
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorFoundationMain)
    ) {
        UiKitCompactAppBar(navigationButton = {
            NavigationButton(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart),
                icon = R.drawable.ic_close
            )
        }, title = "Create")
        BottomSheetMenu(
            menus = listOf(
                BottomSheetMenuSection(
                    id = 0,
                    title = null,
                    menus = menus
                )
            ),
            contentPadding = PaddingValues(16.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewUiKitBottomSheet() {
    UiKitBottomSheet()
}