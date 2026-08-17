package com.renzified.calendar.utils

import com.renzified.calendar.uimodels.UiEvent
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class PackWeekEventsTest {

    @Test
    fun shouldOnlyPackEventsWithinTheRange() {
        val events = listOf(
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
            ),
            UiEvent.AllDay(
                name = "Renz Birthday",
                date = LocalDate(year = 2026, month = 11, day = 10)
            )
        )
        val week = LocalDate(year = 2026, month = 8, day = 3)..LocalDate(year = 2026, month = 8, day = 9)
        val result = packWeekEvents(events = events, dates = week, maxRow = 10)
        assertEquals(1, result.drawn.size)
    }

    @Test
    fun shouldProperlyFillGaps() {
        val events = listOf(
            UiEvent.Range(
                name = "First Event",
                start = LocalDateTime(year = 2026, month = 8, day = 3, hour = 8, minute = 0),
                end = LocalDateTime(year = 2026, month = 8, day = 6, hour = 8, minute = 0)
            ),
            UiEvent.Range(
                name = "Second Event",
                start = LocalDateTime(year = 2026, month = 8, day = 6, hour = 8, minute = 0),
                end = LocalDateTime(year = 2026, month = 8, day = 7, hour = 8, minute = 0)
            ),
            UiEvent.AllDay(
                name = "Third Event",
                date = LocalDate(year = 2026, month = 8, day = 7)
            )
        )
        val week = LocalDate(year = 2026, month = 8, day = 3)..LocalDate(year = 2026, month = 8, day = 9)
        val result = packWeekEvents(events = events, dates = week, maxRow = 10)
        val expected = listOf(
            UiEvent.Range(
                name = "First Event",
                start = LocalDateTime(year = 2026, month = 8, day = 3, hour = 8, minute = 0),
                end = LocalDateTime(year = 2026, month = 8, day = 6, hour = 8, minute = 0)
            ),
            UiEvent.AllDay(
                name = "Third Event",
                date = LocalDate(year = 2026, month = 8, day = 7)
            ),
            UiEvent.Range(
                name = "Second Event",
                start = LocalDateTime(year = 2026, month = 8, day = 6, hour = 8, minute = 0),
                end = LocalDateTime(year = 2026, month = 8, day = 7, hour = 8, minute = 0)
            )
        )
        assertEquals(expected, result.drawn.map { it.event })
        assertEquals(result.drawn.first { it.event.name == "First Event" }.lane, 0)
        assertEquals(result.drawn.first { it.event.name == "Third Event" }.lane, 0)
        assertEquals(result.drawn.first { it.event.name == "Second Event" }.lane, 1)
    }

    @Test
    fun
}