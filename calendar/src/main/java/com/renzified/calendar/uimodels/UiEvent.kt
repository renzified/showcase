package com.renzified.calendar.uimodels

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

sealed interface UiEvent {

    data class AllDay(val name: String, val date: LocalDate) : UiEvent

    data class Range(
        val name: String,
        val start: LocalDateTime,
        val end: LocalDateTime
    ) : UiEvent

    data class Time(val name: String, val timestamp: LocalDateTime) : UiEvent

    data object Measurement: UiEvent
}

data class PackedEvent(val event: UiEvent, val lane: Int)

data class PackedWeekEvents(
    val drawn: List<PackedEvent>,
    val remaining: List<UiEvent>
)