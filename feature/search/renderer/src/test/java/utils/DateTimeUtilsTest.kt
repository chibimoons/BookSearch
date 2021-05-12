package com.pretest.search.renderer.utils

import org.junit.Test

class DateTimeUtilsTest {
    @Test
    fun convertDateFormat() {
        val expectedDateString = "2018.08.31"
        val formattedDateString = DateTimeUtils.getFormattedDateString("2018-08-31T00:00:00.000+09:00")
        assert(expectedDateString.equals(formattedDateString))
    }
}