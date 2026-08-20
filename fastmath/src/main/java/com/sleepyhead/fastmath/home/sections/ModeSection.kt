package com.sleepyhead.fastmath.home.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.showcase.uikit.themes.UiKitTheme
import com.sleepyhead.showcase.uikit.R as UiKitRes

@Composable
internal fun ModeSection(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = "PICK A MODE", style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(220.dp),
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(36.dp),
                    topEnd = CornerSize(12.dp),
                    bottomStart = CornerSize(36.dp),
                    bottomEnd = CornerSize(12.dp)
                ),
                onClick = {}
            ) {
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(UiKitRes.drawable.ic_play_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Drill",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "No timer, just answer",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Card {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(6.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                painter = painterResource(UiKitRes.drawable.ic_footprints_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Sprint", style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "Race the clock",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                Card {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(6.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                painter = painterResource(UiKitRes.drawable.ic_footprints_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Lives", style = MaterialTheme.typography.titleMedium)
                            Text(text = "3 retry only", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                Card {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(6.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                painter = painterResource(UiKitRes.drawable.ic_footprints_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Rank", style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "Climb leaderboard",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewModeSection() {
    UiKitTheme {
        Surface {
            ModeSection()
        }
    }
}