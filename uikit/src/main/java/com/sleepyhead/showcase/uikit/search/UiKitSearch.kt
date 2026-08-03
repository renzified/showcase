package com.sleepyhead.showcase.uikit.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.R
import com.sleepyhead.showcase.uikit.providers.ColorVibrantSecondary
import com.sleepyhead.showcase.uikit.providers.Regular17

@Composable
fun UiKitSearch(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(Color(0xFF0D2336).copy(alpha = 0.05f))
            .padding(11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(R.drawable.ic_search), contentDescription = null)
        BasicText(
            text = stringResource(R.string.uikit_search),
            style = Regular17.copy(color = ColorVibrantSecondary),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewUiKitSearch() {
    Box(modifier = Modifier.background(Color.White)) {
        UiKitSearch(modifier = Modifier.padding(16.dp))
    }
}