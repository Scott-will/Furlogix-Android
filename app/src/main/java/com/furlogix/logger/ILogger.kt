package com.furlogix.logger

interface ILogger {
    fun log(tag : String, message: String?)
    fun logError(tag: String, message: String?, error: Throwable?)
}