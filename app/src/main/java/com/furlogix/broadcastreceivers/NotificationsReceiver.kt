package com.furlogix.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.furlogix.Furlogix
import com.furlogix.logger.ILogger
import com.furlogix.reminders.ReportsNotificationFactory

class NotificationReceiver(
    private val logger : ILogger,) : BroadcastReceiver() {
    private val TAG = "Furlogix:" + NotificationReceiver::class.qualifiedName

    override fun onReceive(context: Context, intent: Intent) {
        logger.log(TAG, "Recieved notification")
        showNotification(context, intent)
    }

    private fun showNotification(context: Context, intent: Intent){
        val notificationManager = Furlogix.getNotificationManager()
        val notifFactory = ReportsNotificationFactory()
        val notif = notifFactory.buildNotificationToSendReports(intent)
        notificationManager.notify(1, notif)
    }
}