package com.sleepyhead.fastmath.home.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.fastmath.R
import com.sleepyhead.showcase.uikit.themes.UiKitTheme

@Composable
internal fun WarmupSection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(36.dp),
            topEnd = CornerSize(12.dp),
            bottomStart = CornerSize(36.dp),
            bottomEnd = CornerSize(36.dp)
        )
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = stringResource(R.string.warm_up),
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "9 x 4", style = MaterialTheme.typography.displaySmall)
            Text(
                text = "Answer before the timer hits 8s",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {}) {
                    Text(text = "Play Now")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWarmupSection() {
    UiKitTheme {
        WarmupSection()
    }
}