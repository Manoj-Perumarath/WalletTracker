package com.manoj.wallettracker.data.room.converters

import androidx.room.TypeConverter
import com.manoj.wallettracker.foundation.core.date.toLocalDateTime
import com.manoj.wallettracker.foundation.core.date.toMillis
import java.time.LocalDateTime

class DateConverter {

    @TypeConverter
    fun toDate(date: Long?): LocalDateTime? {
        if (date == null) return null

        return date.toLocalDateTime()
    }

    @TypeConverter
    fun toDateLong(date: LocalDateTime?): Long? {
        if (date == null) return null

        return date.toMillis()
    }

}