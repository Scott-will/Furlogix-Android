package com.example.vetapp.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.vetapp.VetApplication
import com.example.vetapp.reminders.ReportsNotificationFactory

class SendReportsNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
    }

    private fun showNotification(context: Context){
        val notificationManager = VetApplication.getNotificationManager()
        val notifFactory = ReportsNotificationFactory()
        val notif = notifFactory.buildNotificationToSendReports()
        notificationManager.notify(1, notif)
    }
}

class FillOutReportsNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
    }

    private fun showNotification(context: Context){
        val notificationManager = VetApplication.getNotificationManager()
        val notifFactory = ReportsNotificationFactory()
        val notif = notifFactory.buildNotificationToFillOutReports()
        notificationManager.notify(1, notif)
    }
}

