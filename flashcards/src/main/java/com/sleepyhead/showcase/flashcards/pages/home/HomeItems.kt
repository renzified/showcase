package com.sleepyhead.showcase.flashcards.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.R
import com.sleepyhead.showcase.uikit.providers.ColorBodySecondary
import com.sleepyhead.showcase.uikit.providers.ColorBorderColor
import com.sleepyhead.showcase.uikit.providers.ColorIconDefault
import com.sleepyhead.showcase.uikit.providers.ColorSurfacePrimary
import com.sleepyhead.showcase.uikit.providers.ColorTextBody
import com.sleepyhead.showcase.uikit.providers.ColorTextHeading
import com.sleepyhead.showcase.uikit.providers.Medium14
import com.sleepyhead.showcase.uikit.providers.Medium16

@Composable
fun HomeItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = ColorBorderColor, shape = RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(20.dp)
    ) {
        Row {
            Icon()
            Column(modifier = Modifier.padding(start = 12.dp)) {
                BasicText(text = "Form Name", style = Medium16.copy(color = ColorTextHeading))
                BasicText(text = "Category", style = Medium14.copy(color = ColorTextBody))
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.padding(start = 12.dp)) {
                BasicText(text = "2", style = Medium14.copy(color = ColorBodySecondary))
                Image(
                    painter = painterResource(R.drawable.ic_note),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(start = 4.dp),
                    colorFilter = ColorFilter.tint(color = ColorBodySecondary)
                )
            }
        }
    }
}

@Composable
private fun Icon() {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ColorSurfacePrimary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_note),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(ColorIconDefault)
        )
    }
}

@Preview
@Composable
private fun PreviewHomeItem() {
    HomeItem()
}
