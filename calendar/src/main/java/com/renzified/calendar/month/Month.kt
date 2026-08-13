package com.renzified.calendar.month

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.renzified.calendar.uimodels.UiEvent
import com.renzified.calendar.utils.DateTimeUtils
import com.renzified.calendar.utils.format
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.YearMonth
import java.time.format.TextStyle

data class MonthState(
    val events: List<UiEvent>,
    val month: YearMonth
)

@Composable
fun Month(
    modifier: Modifier = Modifier,
    state: MonthState
) {
    val firstDayOfWeek = remember { mutableStateOf(DateTimeUtils.firstDayOfWeek) }
    val daysOfWeek = remember {
        derivedStateOf {
            DateTimeUtils.getDaysOfWeekWithStartingDay(firstDayOfWeek.value)
        }
    }
    val weeks = remember(state) {
        DateTimeUtils.getWeeksOfMonth(state.month)
    }

    Column(modifier = modifier) {
        DayOfWeekRow(daysOfWeek = daysOfWeek.value)
        CalendarBody(content = weeks)
    }
}

@Composable
fun rememberMonthState(month: YearMonth, events: List<UiEvent>): MonthState {
    return remember {
        MonthState(
            events = events,
            month = month
        )
    }
}

@Preview
@Composable
private fun PreviewMonth() {
    val state = rememberMonthState(
        month = YearMonth(year = 2026, month = 8),
        events = listOf(
            UiEvent.AllDay(
                name = "Ninoy Aquino day",
                date = LocalDate(year = 2026, month = 8, day = 21)
            ),
            UiEvent.AllDay(
                name = "National heroes day",
                date = LocalDate(year = 2026, month = 8, day = 31)
            ),
            UiEvent.Range(
                name = "Meeting",
                start = LocalDateTime(year = 2026, month = 8, day = 3, hour = 8, minute = 0),
                end = LocalDateTime(year = 2026, month = 8, day = 6, hour = 8, minute = 0)
            )
        )
    )

    Month(
        modifier = Modifier.fillMaxSize(),
        state = state
    )
}

@Composable
fun DayOfWeekRow(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        daysOfWeek.forEach { item ->
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .weight(1f)
                    .background(Color.Red)
            ) {
                BasicText(
                    text = item.format(style = TextStyle.SHORT),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ColumnScope.CalendarBody(content: Map<Int, List<LocalDate>>) {
    for ((_, dates) in content) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // create a ref for all the dates
            val dateContainerRef = mutableMapOf<LocalDate, ConstrainedLayoutReference>()
            dates.forEach { item -> dateContainerRef[item] = createRef() }

            dates.forEachIndexed { index, item ->

                val dateContainerRefs = dateContainerRef[item]
                requireNotNull(dateContainerRefs) { "reference to day container is null" }

                Box(
                    modifier = Modifier
                        .constrainAs(dateContainerRefs) {
                            val startLink = if (index == 0) {
                                parent.start
                            } else {
                                val lastDate = dates[index - 1]
                                dateContainerRef[lastDate]!!.end
                            }

                            val endLink = if (index == dates.lastIndex) {
                                parent.end
                            } else {
                                val nextDate = dates[index + 1]
                                dateContainerRef[nextDate]!!.start
                            }

                            start.linkTo(startLink)
                            end.linkTo(endLink)
                            top.linkTo(parent.top)

                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        }

                ) {
                    Day(item.day)
                }

                ConstraintLayout(
                    modifier = Modifier
                        .constrainAs(createRef()) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(dateContainerRefs.bottom)
                            bottom.linkTo(parent.bottom)

                            width = Dimension.matchParent
                            height = Dimension.fillToConstraints
                        }
                ) {

                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Blue)
        )
    }
}

@Composable
fun BoxScope.Day(day: Int) {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 2.dp)
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = day.toString(),
            modifier = Modifier.padding(5.dp),
            autoSize = TextAutoSize.StepBased()
        )
    }
}