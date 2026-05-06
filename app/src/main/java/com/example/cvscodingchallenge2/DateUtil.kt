package com.example.cvscodingchallenge2

class DateUtil {
    fun formatDate(date: String): String {
        return java.time.Instant.parse(date)
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate()
            .toString()
    }
}