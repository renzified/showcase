package com.sleepyhead.fastmath.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sleepyhead.fastmath.R
import com.sleepyhead.showcase.uikit.providers.PixelifySansFontFamily
import com.sleepyhead.showcase.uikit.themes.UiKitTheme
import com.sleepyhead.showcase.uikit.R as UiKitRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = LocalTextStyle.current.copy(fontFamily = PixelifySansFontFamily)
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(UiKitRes.drawable.ic_settings_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = padding.plus(PaddingValues(16.dp))
        ) {
            item(1) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
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

            item(2) {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = MaterialTheme.shapes.medium.copy(
                            topStart = CornerSize(12.dp),
                            topEnd = CornerSize(36.dp),
                            bottomStart = CornerSize(36.dp),
                            bottomEnd = CornerSize(36.dp)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.width(IntrinsicSize.Max),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "20", style = MaterialTheme.typography.titleLarge)
                                HorizontalDivider()
                                Text(text = "Aug", style = MaterialTheme.typography.titleMedium)
                            }
                            Spacer(modifier = Modifier.width(24.dp))
                            Column {
                                Text(
                                    text = "Next Challenge is on",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "12:00 hours",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(UiKitRes.drawable.ic_check_24),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }

            item(3) {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))

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
                                        Text(text = "Race the clock", style = MaterialTheme.typography.bodySmall)
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
                                        Text(text = "Climb leaderboard", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    UiKitTheme {
        HomeScreen()
    }
}