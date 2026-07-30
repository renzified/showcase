package com.sleepyhead.showcase.uikit.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.R

@Composable
fun UiKitAppBar() {
    Row(
        modifier = Modifier.background(Color.White).padding(100.dp)
    ) {
        NavigationButton()
    }
}

@Composable
private fun NavigationButton() {
    Box(
        modifier = Modifier.size(44.dp).background(color = Color.White, shape = CircleShape)
            .innerShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 10.dp,
                    spread = 2.dp,
                    color = Color(0x40000000),
                    offset = DpOffset(x = 6.dp, 7.dp)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(R.drawable.ic_navigation_back), contentDescription = null)
    }
}

@Preview
@Composable
private fun PreviewUiKitAppBar() {
    UiKitAppBar()
}