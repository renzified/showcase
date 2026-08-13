package com.renzified.calendar.utils

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.collections.buildList

class DateTimeUtilTest {

    @Test
    fun monday_StartDayOfWeek_isCorrect() {
        val result = DateTimeUtils.getDaysOfWeekWithStartingDay(DayOfWeek.MONDAY)
        val expected = listOf(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
        )
        assertEquals(expected, result)
    }

    @Test
    fun tuesday_StartDayOfWeek_isCorrect() {
        val result = DateTimeUtils.getDaysOfWeekWithStartingDay(DayOfWeek.TUESDAY)
        val expected = listOf(
            DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.MONDAY
        )
        assertEquals(expected, result)
    }

    @Test
    fun wednesday_StartDayOfWeek_isCorrect() {
        val result = DateTimeUtils.getDaysOfWeekWithStartingDay(DayOfWeek.WEDNESDAY)
        val expected = listOf(
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY
        )
        assertEquals(expected, result)
    }

    @Test
    fun thursday_StartDayOfWeek_isCorrect() {
        val result = DateTimeUtils.getDaysOfWeekWithStartingDay(DayOfWeek.THURSDAY)
        val expected = listOf(
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY
        )
        assertEquals(expected, result)
    }

    @Test
    fun friday_StartDayOfWeek_isCorrect() {
        val result = DateTimeUtils.getDaysOfWeekWithStartingDay(DayOfWeek.FRIDAY)
        val expected = listOf(
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY
        )
        assertEquals(expected, result)
    }

    @Test
    fun saturday_StartDayOfWeek_isCorrect() {
        val result = DateTimeUtils.getDaysOfWeekWithStartingDay(DayOfWeek.SATURDAY)
        val expected = listOf(
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        )
        assertEquals(expected, result)
    }

    @Test
    fun sunday_StartDayOfWeek_isCorrect() {
        val result = DateTimeUtils.getDaysOfWeekWithStartingDay(DayOfWeek.SUNDAY)
        val expected = listOf(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        )
        assertEquals(expected, result)
    }

    @Test
    fun august_2026_should_start_at_week_31() {
        val result = DateTimeUtils.getWeeksOfMonth(YearMonth(year = 2026, month = 8))
        val expected = buildMap {
            val week31 = buildList {
                add(LocalDate(year = 2026, month = 7, 27))
                add(LocalDate(year = 2026, month = 7, 28))
                add(LocalDate(year = 2026, month = 7, 29))
                add(LocalDate(year = 2026, month = 7, 30))
                add(LocalDate(year = 2026, month = 7, 31))
                add(LocalDate(year = 2026, month = 8, 1))
                add(LocalDate(year = 2026, month = 8, 2))
            }
            put(31, week31)

            val week32 = buildList {
                add(LocalDate(year = 2026, month = 8, 3))
                add(LocalDate(year = 2026, month = 8, 4))
                add(LocalDate(year = 2026, month = 8, 5))
                add(LocalDate(year = 2026, month = 8, 6))
                add(LocalDate(year = 2026, month = 8, 7))
                add(LocalDate(year = 2026, month = 8, 8))
                add(LocalDate(year = 2026, month = 8, 9))
            }
            put(32, week32)

            val week33 = buildList {
                add(LocalDate(year = 2026, month = 8, 10))
                add(LocalDate(year = 2026, month = 8, 11))
                add(LocalDate(year = 2026, month = 8, 12))
                add(LocalDate(year = 2026, month = 8, 13))
                add(LocalDate(year = 2026, month = 8, 14))
                add(LocalDate(year = 2026, month = 8, 15))
                add(LocalDate(year = 2026, month = 8, 16))
            }
            put(33, week33)

            val week34 = buildList {
                add(LocalDate(year = 2026, month = 8, 17))
                add(LocalDate(year = 2026, month = 8, 18))
                add(LocalDate(year = 2026, month = 8, 19))
                add(LocalDate(year = 2026, month = 8, 20))
                add(LocalDate(year = 2026, month = 8, 21))
                add(LocalDate(year = 2026, month = 8, 22))
                add(LocalDate(year = 2026, month = 8, 23))
            }
            put(34, week34)

            val week35 = buildList {
                add(LocalDate(year = 2026, month = 8, 24))
                add(LocalDate(year = 2026, month = 8, 25))
                add(LocalDate(year = 2026, month = 8, 26))
                add(LocalDate(year = 2026, month = 8, 27))
                add(LocalDate(year = 2026, month = 8, 28))
                add(LocalDate(year = 2026, month = 8, 29))
                add(LocalDate(year = 2026, month = 8, 30))
            }
            put(35, week35)

            val week36 = buildList {
                add(LocalDate(year = 2026, month = 8, 31))
                add(LocalDate(year = 2026, month = 9, 1))
                add(LocalDate(year = 2026, month = 9, 2))
                add(LocalDate(year = 2026, month = 9, 3))
                add(LocalDate(year = 2026, month = 9, 4))
                add(LocalDate(year = 2026, month = 9, 5))
                add(LocalDate(year = 2026, month = 9, 6))
            }
            put(36, week36)
        }

        assertEquals(expected, result)
    }
}