package com.itechsolution.birrscan.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

sealed class TimeFilter {
    object Today : TimeFilter()
    object Last7Days : TimeFilter()
    data class Custom(val from: Long, val to: Long) : TimeFilter()
}

fun TimeFilter.getTodayTimestamps(): Pair<Long, Long> {
    val now = System.currentTimeMillis()
    val startOfDay = now.startOfDayMillis()  // implement this extension
    val endOfDay = now.endOfDayMillis()
    return Pair(startOfDay, endOfDay)
}


fun TimeFilter.toTimestampRange(): Pair<Long, Long> {
    val now = System.currentTimeMillis()
    return when (this) {
        is TimeFilter.Today -> now.startOfDayMillis() to now.endOfDayMillis()
        is TimeFilter.Last7Days -> (now - 7 * 24 * 60 * 60 * 1000) to now
        is TimeFilter.Custom -> this.from to this.to
    }
}


fun Long.startOfDayMillis(): Long {
    return LocalDate.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun Long.endOfDayMillis(): Long {
    return LocalDate.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .plusDays(1)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli() - 1
}
