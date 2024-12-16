package com.example.vetapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class VetApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: VetApplication? = null
        private var notificationManager : NotificationManager? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        fun getNotificationManager() : NotificationManager{
            return notificationManager!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = this.applicationContext
        createNotificationChannel(context)
    }

    //required for reminders
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "reminder_channel",
                "Reminder Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for reminder notifications"
            }

            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager!!.createNotificationChannel(channel)
        }
    }

}