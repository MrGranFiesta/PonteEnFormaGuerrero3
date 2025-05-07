package com.mrgranfiesta.ponteenformaguerrero3.domain.objects

import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.DateUtils
import java.time.LocalDate

sealed class OptionalLocalDate {
    data class Date(val date: LocalDate) : OptionalLocalDate()
    data object Empty : OptionalLocalDate()

    companion object {
        fun getOptionalByString(
            format: String,
            date: String
        ) : OptionalLocalDate = try {
            Date(
                DateUtils.getLocalDate(
                    format = format,
                    date = date
                )
            )
        } catch (_: Exception) {
            Empty
        }
    }

    fun getStringDateOrEmpty(format: String): String {
        return try {
            when (this) {
                is Date -> DateUtils.toStringFormat(this.date, format)
                is Empty -> ""
            }
        } catch (_ : Exception) {
            ""
        }
    }

    fun isBefore(other: OptionalLocalDate) = when (this) {
        is Date -> when (other) {
            is Date -> this.date.isBefore(other.date)
            Empty -> false
        }

        Empty -> false
    }
}
