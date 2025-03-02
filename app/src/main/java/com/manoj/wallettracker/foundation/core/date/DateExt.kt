package com.manoj.wallettracker.foundation.core.date

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Locale

fun LocalDateTime.format(pattern: String, zoneId: ZoneId = ZoneId.systemDefault()): String {
    val locale = Locale.getDefault()

    return SimpleDateFormat(pattern, locale).format(atZone(zoneId).toInstant().toEpochMilli())
}

fun Long.toLocalDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zoneId)
}

fun LocalDate.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.of(this, LocalTime.of(0, 0, 0, 0))
}

fun LocalDateTime.formatDateTime(): String {
    val pattern = "EEE, dd MMM yyyy"
    return format(pattern)
}


fun LocalDateTime.toMillis(zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return atZone(zoneId).toInstant().toEpochMilli()
}

fun LocalDate.toMillis(zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return atStartOfDay(zoneId).toInstant().toEpochMilli()
}


fun LocalDateTime.generateThisMonthDateRange(): Pair<LocalDate, LocalDate> {
    return Pair(toLocalDate().withDayOfMonth(1), this.toLocalDate().plusDays(1))
}

fun LocalDateTime.generateThisMonthDateTimeRange(): Pair<LocalDateTime, LocalDateTime> {
    val dateRange = generateThisMonthDateRange()
    return Pair(dateRange.first.toLocalDateTime(), dateRange.second.toLocalDateTime())
}
