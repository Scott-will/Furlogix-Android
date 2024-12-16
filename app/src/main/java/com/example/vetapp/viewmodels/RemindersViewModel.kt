package com.example.vetapp.viewmodels

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.vetapp.VetApplication
import com.example.vetapp.broadcastreceivers.FillOutReportsNotificationReceiver
import com.example.vetapp.broadcastreceivers.SendReportsNotificationReceiver

class RemindersViewModel {

    private val TAG = "VetApp:" + ReportViewModel::class.qualifiedName

    private fun scheduleReminder(type : String){
        val context = VetApplication.applicationContext()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent : Intent
        when(type){
            "Send" -> intent = Intent(context, SendReportsNotificationReceiver::class.java)
            "Fill" -> intent = Intent(context, FillOutReportsNotificationReceiver::class.java)
            else -> return

        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


       // val triggerTimeMillis = SystemClock.elapsedRealtime() + TimeUnit.MINUTES.toMillis(minutes)
       // alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTimeMillis, pendingIntent)
        Log.d(TAG, "Set a reminder for ${type}")
    }
}