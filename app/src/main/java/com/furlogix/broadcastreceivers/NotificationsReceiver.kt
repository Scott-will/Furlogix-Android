package com.furlogix.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.furlogix.Furlogix
import com.furlogix.reminders.ReportsNotificationFactory

class NotificationReceiver : BroadcastReceiver() {
    private val TAG = "Furlogix:" + NotificationReceiver::class.qualifiedName

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Recieved notification")
        showNotification(context, intent)
    }

    private fun showNotification(context: Context, intent: Intent){
        val notificationManager = Furlogix.getNotificationManager()
        val notifFactory = ReportsNotificationFactory()
        val notif = notifFactory.buildNotificationToSendReports(intent)
        notificationManager.notify(1, notif)
    }
}