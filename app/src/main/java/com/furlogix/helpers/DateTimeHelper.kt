package com.furlogix.helpers

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeHelper {
    @SuppressLint("NewApi")
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    @SuppressLint("NewApi")
    public fun FormatDateTimeString(dt : LocalDateTime) : String{
        return dt.format(formatter)
    }
}