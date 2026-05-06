package com.example.cvscodingchallenge2.util

import java.time.Instant
import java.time.ZoneId

class DateUtil {
    fun formatDate(date: String): String {
        return Instant.parse(date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .toString()
    }
}