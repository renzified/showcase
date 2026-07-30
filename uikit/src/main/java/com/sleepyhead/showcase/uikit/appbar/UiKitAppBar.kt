package com.sleepyhead.showcase.uikit.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.R
import com.sleepyhead.showcase.uikit.providers.ColorFoundationMain
import com.sleepyhead.showcase.uikit.providers.ColorPlatinum
import com.sleepyhead.showcase.uikit.providers.ColorVibrantSecondary
import com.sleepyhead.showcase.uikit.providers.HeadlineRegular
import com.sleepyhead.showcase.uikit.providers.Regular17

@Composable
fun UiKitAppBar() {
    Box {
        NavigationButton(modifier = Modifier.padding(start = 16.dp))
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {

                BasicText(
                    text = "Details",
                    style = HeadlineRegular,
                    modifier = Modifier.padding(top = 13.dp)
                )

            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(Color(0xFF0D2336).copy(alpha = 0.05f))
                    .padding(11.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(R.drawable.ic_search), contentDescription = null)
                BasicText(text = "Search", style = Regular17.copy(color = ColorVibrantSecondary), modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
private fun NavigationButton(modifier: Modifier) {
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
            Image(painter = painterResource(R.drawable.ic_navigation_back), contentDescription = null, modifier = Modifier.wrapContentSize())
        }
    }
}

@Preview
@Composable
private fun PreviewUiKitAppBar() {
    Box(modifier = Modifier.fillMaxSize().background(ColorFoundationMain)) {
        UiKitAppBar()
    }
}