package com.example.vetapp.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.vetapp.VetApplication
import com.example.vetapp.reminders.ReportsNotificationFactory
import com.example.vetapp.viewmodels.ReportViewModel

class SendReportsNotificationReceiver : BroadcastReceiver() {
    private val TAG = "VetApp:" + SendReportsNotificationReceiver::class.qualifiedName

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Recieved notification")
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
    private val TAG = "VetApp:" + FillOutReportsNotificationReceiver::class.qualifiedName
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Recieved notification")
        showNotification(context)
    }

    private fun showNotification(context: Context){
        val notificationManager = VetApplication.getNotificationManager()
        val notifFactory = ReportsNotificationFactory()
        val notif = notifFactory.buildNotificationToFillOutReports()
        notificationManager.notify(1, notif)
    }
}

