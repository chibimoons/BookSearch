package com.pretest.search.renderer.utils

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

object DateTimeUtils {

    const val DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    const val DATETIME_PATTERN_A_TYPE = "yyyy.MM.dd"

    fun getFormattedDateString(dateString: String?): String {
        return try {
            val dateTimeFormatter: DateTimeFormatter = DateTimeFormat.forPattern(DATETIME_PATTERN)
            val localDate = dateTimeFormatter.parseLocalDate(dateString)

            localDate.toString(DATETIME_PATTERN_A_TYPE)
        } catch (e: Exception) {
            ""
        }

    }
}