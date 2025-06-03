package com.furlogix.logger

import android.util.Log
import javax.inject.Inject

class LogcatLogger @Inject constructor() : ILogger {
    override fun log(tag : String, message: String?) {
        Log.d(tag, message!!)
    }

    override fun logError(tag : String, message: String?, error: Throwable?) {
        Log.e(tag, message, error)
    }
}