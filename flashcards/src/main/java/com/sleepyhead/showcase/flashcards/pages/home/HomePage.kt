package com.sleepyhead.showcase.flashcards.pages.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.appbar.UiKitAppBar
import com.sleepyhead.showcase.uikit.bottomsheet.UiKitBottomSheet
import com.sleepyhead.showcase.uikit.button.UiKitTextButton
import com.sleepyhead.showcase.uikit.providers.ColorBorderColor
import com.sleepyhead.showcase.uikit.providers.ColorFoundationMain
import com.sleepyhead.showcase.uikit.providers.ColorSurfaceAction
import com.sleepyhead.showcase.uikit.search.UiKitSearch
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Composable
fun HomePage() {
    val topInset = WindowInsets.statusBars.only(WindowInsetsSides.Top)

    var moved by remember { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorFoundationMain)
                .windowInsetsPadding(topInset)
        ) {
            UiKitAppBar(title = "Flashcards") {
                UiKitSearch(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 10.dp)
                )

                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(ColorBorderColor)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(count = 12) {
                        HomeItem()
                    }
                }


                Box(
                    modifier = Modifier
                        .background(

                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White
                                ),
                                startY = 0f,
                                endY = 24f
                            )

                        )
                        .padding(bottom = 16.dp, top = 24.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    UiKitTextButton(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Create",
                        color = ColorSurfaceAction
                    ) {
                        moved = !moved
                    }
                }
            }
        }

        //UiKitBottomSheet(modifier = Modifier.align(Alignment.BottomCenter), show = moved)
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    HomePage()
}