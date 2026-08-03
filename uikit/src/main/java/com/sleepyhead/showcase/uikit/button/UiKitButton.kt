package com.sleepyhead.showcase.uikit.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.providers.ColorSurfaceAction
import com.sleepyhead.showcase.uikit.providers.ColorTextOnAction
import com.sleepyhead.showcase.uikit.providers.Medium16

@Composable
fun UiKitTextButton(modifier: Modifier = Modifier, text: String, color: Color) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = color),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasicText(text = text, style = Medium16.copy(color = ColorTextOnAction))
    }
}

@Preview
@Composable
private fun PreviewUiKitTextButton() {
    UiKitTextButton(text = "Submit", color = ColorSurfaceAction)
}