package com.renzified.calendar.utils

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.Instant

object DateTimeUtils {

    val now: Instant
        get() = Clock.System.now()

    val firstDayOfWeek: DayOfWeek
        get() {
            val javaDay = WeekFields.of(Locale.getDefault()).firstDayOfWeek
            return DayOfWeek.valueOf(javaDay.name)
        }

    fun getDaysOfWeekWithStartingDay(day: DayOfWeek): List<DayOfWeek> {
        val days = DayOfWeek.entries.toTypedArray()
        val index = days.indexOf(day)
        val first = days.slice(0..<index)
        val remaining = days.slice(index + 1..days.lastIndex)
        return buildList {
            add(day)
            addAll(remaining)
            addAll(first)
        }
    }

    fun getWeeksOfMonth(month: YearMonth, firstDay: DayOfWeek = DayOfWeek.MONDAY): Map<Int, List<LocalDate>> {
        val firstDayOfMonth = LocalDate(year = month.year, month = month.month.number, day = 1)
        val lastDayOfMonth = month.lastDayOfMonth()

        val firstWeekNumber = firstDayOfMonth.weekOfYear()
        val lastWeekNumber = lastDayOfMonth.weekOfYear()

        return buildMap {
            (firstWeekNumber..lastWeekNumber).forEach { week ->
                put(week, datesOfWeek(year = month.year, weekNumber = week, firstDay = firstDay))
            }
        }
    }

    fun datesOfWeek(year: Int, weekNumber: Int, firstDay: DayOfWeek = DayOfWeek.MONDAY): List<LocalDate> {
        // ISO rule: week 1 is the week containing Jan 4
        val jan4 = LocalDate(year, 1, 4)
        val firstWeekStart = jan4.startOfWeek(firstDay)

        // Offset by (weekNumber - 1) * 7 days
        val targetWeekStart = firstWeekStart.plus(DatePeriod(days = (weekNumber - 1) * 7))

        return (0..6).map { targetWeekStart.plus(DatePeriod(days = it)) }
    }
}

fun DayOfWeek.format(
    locale: Locale = Locale.getDefault(),
    style: TextStyle = TextStyle.FULL
): String {
    val javaDay = java.time.DayOfWeek.valueOf(this.name)
    return javaDay.getDisplayName(style, locale)
}

fun LocalDate.weekOfYear(locale: Locale = Locale.getDefault()): Int {
    val javaDate = java.time.LocalDate.of(year, month.number, day)
    val weekFields = WeekFields.of(locale)
    return javaDate.get(weekFields.weekOfYear())
}

fun YearMonth.lastDayOfMonth(): LocalDate {
    val daysInMonth = when (month) {
        Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY,
        Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
    }
    return LocalDate(year, month.number, daysInMonth)
}

fun isLeapYear(year: Int): Boolean =
    (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

fun LocalDate.startOfWeek(firstDay: DayOfWeek = DayOfWeek.MONDAY): LocalDate {
    var date = this
    while (date.dayOfWeek != firstDay) {
        date = date.minus(1, DateTimeUnit.DAY)
    }
    return date
}