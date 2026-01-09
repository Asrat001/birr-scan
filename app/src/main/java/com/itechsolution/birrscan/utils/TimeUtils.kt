package com.itechsolution.birrscan.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.LocalDate
import java.util.Date
import java.util.Locale

object TimeUtils {

    fun todayRange(): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()

        val today: LocalDate = LocalDate.now(zone)

        val start = today
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        val end = start + 86_399_999 // 23:59:59.999

        return start to end
    }

    fun last7Days(): Long {
        return Instant.now()
            .minusSeconds(7 * 24 * 60 * 60)
            .toEpochMilli()
    }
    fun Long.toHumanReadableDate(
        pattern: String = "yyyy/MM/dd HH:mm",
        locale: Locale = Locale.getDefault()
    ): String {
        val sdf = SimpleDateFormat(pattern, locale)
        val date = Date(this)
        return sdf.format(date)
    }

}

