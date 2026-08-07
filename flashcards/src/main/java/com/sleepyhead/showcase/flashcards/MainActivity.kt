package com.sleepyhead.showcase.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                var state by remember { mutableStateOf(UiKitBottomSheet.State.HIDDEN) }

                UiKitBottomSheet(
                    state = state,
                    height = UiKitBottomSheet.HeightBehavior.Full(peekHeight = UiKitBottomSheet.HeightBehavior.Full.PeekHeight.Half)
                )

                Column(modifier = Modifier.align(Alignment.Center)) {
                    UiKitTextButton(text = "Open", color = ColorSurfaceAction) {
                        state = UiKitBottomSheet.State.EXPANDED
                    }

                    UiKitTextButton(text = "Close", color = ColorSurfaceAction) {
                        state = UiKitBottomSheet.State.HIDDEN
                    }
                }
            }
        }
    }
}