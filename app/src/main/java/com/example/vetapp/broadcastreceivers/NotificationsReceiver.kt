package com.example.vetapp.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.vetapp.VetApplication
import com.example.vetapp.reminders.ReportsNotificationFactory

class NotificationReceiver : BroadcastReceiver() {
    private val TAG = "VetApp:" + NotificationReceiver::class.qualifiedName

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Recieved notification")
        showNotification(context, intent)
    }

    private fun showNotification(context: Context, intent: Intent){
        val notificationManager = VetApplication.getNotificationManager()
        val notifFactory = ReportsNotificationFactory()
        val notif = notifFactory.buildNotificationToSendReports(intent)
        notificationManager.notify(1, notif)
    }
}