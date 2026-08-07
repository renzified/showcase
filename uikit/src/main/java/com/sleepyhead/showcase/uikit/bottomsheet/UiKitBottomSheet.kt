package com.sleepyhead.showcase.uikit.bottomsheet

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.providers.ColorFoundationMain

object UiKitBottomSheet {

    enum class State {
        HIDDEN,
        PEEK,
        EXPANDED
    }

    sealed interface HeightBehavior {

        data object Wrap : HeightBehavior

        data class Full(val peekHeight: PeekHeight) : HeightBehavior {

            sealed interface PeekHeight {

                data class Px(val height: Int) : PeekHeight
                data object Half : PeekHeight
            }
        }
    }
}

@Composable
fun UiKitBottomSheet(
    state: UiKitBottomSheet.State,
    height: UiKitBottomSheet.HeightBehavior
) {
    if (state != UiKitBottomSheet.State.HIDDEN) {

        var ready by remember { mutableStateOf(false) }
        var heightPx by remember { mutableIntStateOf(0) }
        var currentState by remember { mutableStateOf<UiKitBottomSheet.State>() }
        var operation by remember { mutableStateOf<Pair<UiKitBottomSheet.State, UiKitBottomSheet.State>?>(null) }

        val heightState = when (height) {
            is UiKitBottomSheet.HeightBehavior.Wrap -> Modifier.wrapContentHeight()
            is UiKitBottomSheet.HeightBehavior.Full -> Modifier.fillMaxHeight()
        }

        val animateOffsetY = remember { Animatable(0f) }

        LaunchedEffect(heightPx, ready) {
            if (!ready && heightPx > 0) {
                animateOffsetY.animateTo(targetValue = -heightPx.toFloat())
                ready = true
            }
        }

        LaunchedEffect(state) {
            //operation.
        }

        LaunchedEffect(animateOffsetY.value) {
            Log.i("spawned", animateOffsetY.value.toString())
        }


        Column(
            modifier = Modifier
                .graphicsLayer {
                    translationY = animateOffsetY.value
                    alpha = if (ready) 1f else 0f
                }
                .then(heightState)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(ColorFoundationMain)
                .onSizeChanged {
                    heightPx = it.height
                }
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Red))
        }
    }
}

@Preview
@Composable
private fun PreviewUiKitBottomSheet() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

    }
}