package com.mrgranfiesta.ponteenformaguerrero3.domain.utils


import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class DateUtils {
    companion object {
        fun getSecondsToMiliseconds(seconds: Int) = seconds * 1000L
        fun getMinusFormat(
            initTime: LocalDateTime,
            endTime: LocalDateTime
        ): String =
            StringUtils.timeHourDigitalFormat(Duration.between(initTime, endTime).toMillis())

        fun toStringFormat(
            date: LocalDateTime,
            format: String
        ): String = date.format(DateTimeFormatter.ofPattern(format))

        fun toStringFormat(
            date: LocalDate,
            format: String
        ): String = date.format(DateTimeFormatter.ofPattern(format))

        fun getLocalDate(
            format: String,
            date: String
        ) : LocalDate {
            val formatter = DateTimeFormatter.ofPattern(format)
            return LocalDate.parse(date, formatter)
        }

        fun isNotValidFormat(
            date: String,
            format: String
        ): Boolean {
            return try {
                val formatter = DateTimeFormatter.ofPattern(format)
                LocalDate.parse(date, formatter)
                false
            } catch (_: Exception) {
                true
            }
        }

        fun getMillisToLocalDateTime(
            format: String,
            millis: Long
        ): String = this.toStringFormat(
            format = format,
            date = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        )

        fun getStringToMillis(
            format: String,
            date: String
        ): Long? {
            return try {
                LocalDate.parse(
                    date,
                    DateTimeFormatter.ofPattern(format)
                ).atStartOfDay()
                    .atZone(ZoneId.of("UTC"))
                    .toInstant()
                    .toEpochMilli()
            } catch (_: DateTimeParseException) {
                null
            }
        }
    }
}