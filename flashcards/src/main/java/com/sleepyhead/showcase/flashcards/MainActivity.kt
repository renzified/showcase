package com.sleepyhead.showcase.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.bottomsheet.UiKitBottomSheet
import com.sleepyhead.showcase.uikit.button.UiKitTextButton
import com.sleepyhead.showcase.uikit.providers.ColorSurfaceAction

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*val navController = rememberNavController()
            MainGraph(navController)*/

            Box(modifier = Modifier.fillMaxSize()) {
                var state by rememberSaveable { mutableStateOf(UiKitBottomSheet.State.HIDDEN) }

                Column(modifier = Modifier.align(Alignment.Center)) {
                    UiKitTextButton(text = "Open", color = ColorSurfaceAction) {
                        state = UiKitBottomSheet.State.EXPANDED
                    }

                    UiKitTextButton(text = "Peek", color = ColorSurfaceAction) {
                        state = UiKitBottomSheet.State.PEEK
                    }

                    UiKitTextButton(text = "Close", color = ColorSurfaceAction) {
                        state = UiKitBottomSheet.State.HIDDEN
                    }
                }

                UiKitBottomSheet(
                    state = state,
                    height = UiKitBottomSheet.HeightBehavior.Full(
                        peekHeight = UiKitBottomSheet.HeightBehavior.Full.PeekHeight.Half,
                    ),
                    onStateChange = { state = it },
                ) {
                    BasicText(
                        text = "Bottom sheet content\nDrag me",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                    )
                }
            }
        }
    }
}
