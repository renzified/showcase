package com.renzified.calendar.utils

import com.renzified.calendar.uimodels.PackedEvent
import com.renzified.calendar.uimodels.PackedWeekEvents
import com.renzified.calendar.uimodels.UiEvent
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

fun packWeekEvents(
    maxRow: Int,
    dates: ClosedRange<LocalDate>,
    events: List<UiEvent>
): PackedWeekEvents {
    val weekStart = dates.start
    val weekEnd = dates.endInclusive
    val overlapping = events.filter { it.overlaps(weekStart, weekEnd) }

    if (overlapping.isEmpty() || weekStart > weekEnd) {
        return PackedWeekEvents(drawn = emptyList(), remaining = emptyList())
    }
    if (maxRow <= 0) {
        return PackedWeekEvents(drawn = emptyList(), remaining = overlapping)
    }

    val rowCount = mutableMapOf<LocalDate, Int>()
    var cursor = weekStart
    while (cursor <= weekEnd) {
        rowCount[cursor] = 0
        cursor = cursor.plus(DatePeriod(days = 1))
    }

    val drawn = mutableListOf<PackedEvent>()
    val remaining = overlapping.filter { it.isStartedInWeek(weekStart) }.toMutableList()
    var priority = overlapping.filterNot { it.isStartedInWeek(weekStart) }
    val leftoverPriority = mutableListOf<UiEvent>()

    if (priority.size > maxRow) {
        leftoverPriority.addAll(priority.drop(maxRow))
        priority = priority.take(maxRow)
    }

    placeEvents(
        toPlace = priority,
        remaining = remaining,
        maxEventCount = maxRow,
        weekStart = weekStart,
        weekEnd = weekEnd,
        rowCount = rowCount,
        drawn = drawn
    )

    remaining.addAll(leftoverPriority)
    val expectedToBePlotted = remaining.size
    var plottedCount = 0

    while (true) {
        val sortedByLowestRowCount = rowCount.entries.sortedBy { it.value }
        var shouldStop = false
        var placedThisPass = false

        for (entry in sortedByLowestRowCount) {
            val date = entry.key
            val frozen = remaining.toList()

            for (item in frozen) {
                if (item.placementStart(weekStart) != date) continue

                if (rowCount.getOrDefault(date, 0) >= maxRow) {
                    shouldStop = true
                    break
                }

                placeEvents(
                    toPlace = listOf(item),
                    remaining = mutableListOf(),
                    maxEventCount = maxRow,
                    weekStart = weekStart,
                    weekEnd = weekEnd,
                    rowCount = rowCount,
                    drawn = drawn
                )
                remaining.remove(item)
                plottedCount++
                placedThisPass = true
                break
            }

            if (placedThisPass || shouldStop) break
        }

        if (expectedToBePlotted == plottedCount || shouldStop || !placedThisPass) break
    }

    return PackedWeekEvents(drawn = drawn, remaining = remaining.toList())
}

private fun placeEvents(
    toPlace: List<UiEvent>,
    remaining: MutableList<UiEvent>,
    maxEventCount: Int,
    weekStart: LocalDate,
    weekEnd: LocalDate,
    rowCount: MutableMap<LocalDate, Int>,
    drawn: MutableList<PackedEvent>
) {
    for (event in toPlace) {
        val clippedStart = event.placementStart(weekStart)
        val clippedEnd = event.placementEnd(weekEnd)
        val lane = rowCount.getOrDefault(clippedStart, 0)
        if (lane >= maxEventCount) continue

        drawn += PackedEvent(event = event, lane = lane)

        var day = clippedStart
        while (day <= clippedEnd) {
            rowCount[day] = rowCount.getOrDefault(day, 0) + 1
            day = day.plus(DatePeriod(days = 1))
        }

        if (remaining.isEmpty()) continue
        if (event.spanEnd >= weekEnd) continue

        fillRemainingGap(
            maxEventCount = maxEventCount,
            gapStart = clippedEnd.plus(DatePeriod(days = 1)),
            gapEnd = weekEnd,
            remaining = remaining,
            weekStart = weekStart,
            weekEnd = weekEnd,
            rowCount = rowCount,
            drawn = drawn,
            fillUpToRow = rowCount.values.max()
        )
    }
}

private fun fillRemainingGap(
    maxEventCount: Int,
    gapStart: LocalDate,
    gapEnd: LocalDate,
    remaining: MutableList<UiEvent>,
    weekStart: LocalDate,
    weekEnd: LocalDate,
    rowCount: MutableMap<LocalDate, Int>,
    drawn: MutableList<PackedEvent>,
    fillUpToRow: Int
) {
    var dateCursor = gapStart
    while (dateCursor <= gapEnd) {
        var shouldStop = false
        val frozen = remaining.toList()

        for (item in frozen) {
            if (item.placementStart(weekStart) != dateCursor) continue

            val occupied = rowCount.getOrDefault(dateCursor, 0)
            if (occupied >= maxEventCount) {
                shouldStop = true
                break
            }
            if (occupied >= fillUpToRow) continue

            placeEvents(
                toPlace = listOf(item),
                remaining = mutableListOf(),
                maxEventCount = maxEventCount,
                weekStart = weekStart,
                weekEnd = weekEnd,
                rowCount = rowCount,
                drawn = drawn
            )
            remaining.remove(item)

            if (item.placementEnd(weekEnd) == weekEnd) {
                shouldStop = true
                break
            }
        }

        if (shouldStop) break
        dateCursor = dateCursor.plus(DatePeriod(days = 1))
    }
}

val UiEvent.name: String
    get() = when (this) {
        is UiEvent.AllDay -> name
        is UiEvent.Range -> name
        is UiEvent.Time -> name
    }

private val UiEvent.spanStart: LocalDate
    get() = when (this) {
        is UiEvent.AllDay -> date
        is UiEvent.Range -> start.date
        is UiEvent.Time -> timestamp.date
    }

private val UiEvent.spanEnd: LocalDate
    get() = when (this) {
        is UiEvent.AllDay -> date
        is UiEvent.Range -> end.date
        is UiEvent.Time -> timestamp.date
    }

private fun UiEvent.overlaps(weekStart: LocalDate, weekEnd: LocalDate): Boolean =
    spanEnd >= weekStart && spanStart <= weekEnd

private fun UiEvent.isStartedInWeek(weekStart: LocalDate): Boolean = spanStart >= weekStart

private fun UiEvent.placementStart(weekStart: LocalDate): LocalDate =
    if (spanStart < weekStart) weekStart else spanStart

private fun UiEvent.placementEnd(weekEnd: LocalDate): LocalDate =
    if (spanEnd > weekEnd) weekEnd else spanEnd