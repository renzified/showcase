package com.sleepyhead.showcase.uikit.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import com.sleepyhead.showcase.uikit.providers.ColorPlatinum

@Composable
fun NavigationButton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(44.dp)
            .background(color = Color.White, shape = CircleShape)
            .innerShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 2.dp,
                    spread = 1.dp,
                    color = ColorPlatinum,
                    offset = DpOffset(x = 0.dp, 0.dp)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.ic_navigation_back),
                contentDescription = null,
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewNavigationButton() {
    NavigationButton()
}
