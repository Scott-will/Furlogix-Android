package com.furlogix.reminders
import com.furlogix.Database.AppDatabase
import javax.inject.Inject

class RequestCodeFactory @Inject constructor(private val db: AppDatabase) {

    private var latestRequestCode = 0

    fun getRequestCode(): Int {
        if (latestRequestCode >= Int.MAX_VALUE) {
            latestRequestCode = 0
        }
        latestRequestCode += 1
        return latestRequestCode
    }

    fun initRequestCode() {
        val reminder = db.remindersDao().getLargestRequestCode()
        latestRequestCode = reminder?.requestCode ?: 1
    }
}
