package com.sleepyhead.showcase.uikit.bottomsheet

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.providers.ColorFoundationMain
import kotlin.math.max
import kotlin.math.roundToInt
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter

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
    height: UiKitBottomSheet.HeightBehavior,
    onStateChange: (UiKitBottomSheet.State) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val targetState = resolveTarget(state, height)
    val latestTarget by rememberUpdatedState(targetState)
    val latestOnStateChange by rememberUpdatedState(onStateChange)

    val dragState = remember {
        AnchoredDraggableState(initialValue = targetState)
    }

    val isSheetActive =
        targetState != UiKitBottomSheet.State.HIDDEN ||
            dragState.currentValue != UiKitBottomSheet.State.HIDDEN ||
            dragState.targetValue != UiKitBottomSheet.State.HIDDEN ||
            dragState.isAnimationRunning

    if (!isSheetActive) return

    var anchorsReady by remember { mutableStateOf(false) }
    var sheetHeightPx by remember { mutableIntStateOf(0) }

    LaunchedEffect(targetState, anchorsReady) {
        if (!anchorsReady) return@LaunchedEffect
        if (dragState.targetValue != targetState) {
            dragState.animateTo(targetState, tween(durationMillis = 300))
        }
    }

    LaunchedEffect(dragState, anchorsReady) {
        if (!anchorsReady) return@LaunchedEffect
        snapshotFlow { dragState.settledValue }
            // Skip the parked HIDDEN value from first updateAnchors.
            .drop(1)
            .distinctUntilChanged()
            .filter { settled -> settled != latestTarget }
            .collect { settled -> latestOnStateChange(settled) }
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val containerHeightPx = constraints.maxHeight.toFloat()

        LaunchedEffect(containerHeightPx, sheetHeightPx, height) {
            Log.i("spawned", "$sheetHeightPx")
            if (containerHeightPx <= 0f || sheetHeightPx <= 0) return@LaunchedEffect

            val anchors = buildAnchors(
                containerHeight = containerHeightPx,
                sheetHeight = sheetHeightPx.toFloat(),
                height = height,
            )
            val newTarget = if (!anchorsReady) {
                // Enter: park off-screen then animate. Restore: snap to saved state.
                if (dragState.currentValue == UiKitBottomSheet.State.HIDDEN &&
                    targetState != UiKitBottomSheet.State.HIDDEN
                ) {
                    UiKitBottomSheet.State.HIDDEN
                } else {
                    targetState
                }
            } else {
                val candidate = dragState.targetValue
                if (anchors.hasPositionFor(candidate)) candidate
                else UiKitBottomSheet.State.EXPANDED
            }
            dragState.updateAnchors(anchors, newTarget)
            anchorsReady = true
        }

        val heightModifier = when (height) {
            is UiKitBottomSheet.HeightBehavior.Wrap -> Modifier.wrapContentHeight()
            is UiKitBottomSheet.HeightBehavior.Full -> Modifier.fillMaxHeight()
        }

        val sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .then(heightModifier)
                .onSizeChanged { sheetHeightPx = it.height }
                .offset {
                    val y = dragState.offset
                    IntOffset(
                        x = 0,
                        y = if (y.isNaN()) containerHeightPx.roundToInt() else y.roundToInt(),
                    )
                }
                .anchoredDraggable(
                    state = dragState,
                    orientation = Orientation.Vertical,
                )
                .shadow(elevation = 8.dp, shape = sheetShape)
                .clip(sheetShape)
                .background(ColorFoundationMain),
            content = content,
        )
    }
}

private fun resolveTarget(
    state: UiKitBottomSheet.State,
    height: UiKitBottomSheet.HeightBehavior,
): UiKitBottomSheet.State {
    return if (height is UiKitBottomSheet.HeightBehavior.Wrap &&
        state == UiKitBottomSheet.State.PEEK
    ) {
        UiKitBottomSheet.State.EXPANDED
    } else {
        state
    }
}

/**
 * Material-style anchors: offset is distance from the top of the container.
 * Expanded sits the sheet on the bottom edge; Hidden pushes it fully below.
 */
private fun buildAnchors(
    containerHeight: Float,
    sheetHeight: Float,
    height: UiKitBottomSheet.HeightBehavior,
): DraggableAnchors<UiKitBottomSheet.State> {
    return DraggableAnchors {
        UiKitBottomSheet.State.EXPANDED at max(0f, containerHeight - sheetHeight)
        when (height) {
            is UiKitBottomSheet.HeightBehavior.Wrap -> {
                UiKitBottomSheet.State.HIDDEN at containerHeight
            }
            is UiKitBottomSheet.HeightBehavior.Full -> {
                val peekVisible = when (val peek = height.peekHeight) {
                    is UiKitBottomSheet.HeightBehavior.Full.PeekHeight.Half -> containerHeight / 2f
                    is UiKitBottomSheet.HeightBehavior.Full.PeekHeight.Px ->
                        peek.height.toFloat().coerceIn(0f, containerHeight)
                }
                UiKitBottomSheet.State.PEEK at (containerHeight - peekVisible)
                UiKitBottomSheet.State.HIDDEN at containerHeight
            }
        }
    }
}

@Preview
@Composable
private fun PreviewUiKitBottomSheet() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        UiKitBottomSheet(
            state = UiKitBottomSheet.State.PEEK,
            height = UiKitBottomSheet.HeightBehavior.Full(
                peekHeight = UiKitBottomSheet.HeightBehavior.Full.PeekHeight.Half,
            ),
            onStateChange = {},
        ) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}
