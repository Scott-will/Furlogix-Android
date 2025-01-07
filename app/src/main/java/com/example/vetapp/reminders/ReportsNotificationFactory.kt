package com.example.vetapp.reminders

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.vetapp.MainActivity
import com.example.vetapp.R
import com.example.vetapp.VetApplication

class ReportsNotificationFactory {

    fun buildNotificationToFillOutReports() : Notification{
        val context = VetApplication.applicationContext()
        val intent = BuildIntent(context)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        var builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Fill out Report")
            .setContentText("Remember to fill out your report for your pet")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            //open app when notification is tapped
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        return builder.build()
    }

    fun buildNotificationToSendReports() : Notification{
        val context = VetApplication.applicationContext()
        val intent = BuildIntent(context)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        var builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Send your Report")
            .setContentText("Remember to send your report for your pet")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            //open app when notification is tapped
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        return builder.build()
    }

    private fun BuildIntent(context : Context) : Intent{
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return intent
    }
}