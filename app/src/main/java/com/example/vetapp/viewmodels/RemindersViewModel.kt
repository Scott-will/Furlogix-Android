package com.example.vetapp.viewmodels

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.vetapp.VetApplication
import com.example.vetapp.broadcastreceivers.FillOutReportsNotificationReceiver
import com.example.vetapp.broadcastreceivers.SendReportsNotificationReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor() : ViewModel() {

    private val TAG = "VetApp:" + ReportViewModel::class.qualifiedName

    public fun scheduleReminder(date : String, time : String, recurrence : String, type : String){
        val context = VetApplication.applicationContext()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent : Intent
        when(type){
            "Send" -> intent = Intent(context, SendReportsNotificationReceiver::class.java)
            "Fill" -> intent = Intent(context, FillOutReportsNotificationReceiver::class.java)
            else -> return
        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        try {
            // Combine the date and time into a single string for parsing
            val dateTimeString = "$date $time"
            val dateTime = dateTimeFormat.parse(dateTimeString)
            calendar.time = dateTime
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        when (recurrence) {
            "Once" -> {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
            "Daily" -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
            }
            "Weekly" -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY * 7,
                    pendingIntent
                )
            }
            // Add more recurrence types as needed
            else -> return
        }
        Log.d(TAG, "Set a reminder for ${type}")
    }

    fun checkAndRequestExactAlarmPermission() {
        val context = VetApplication.applicationContext()

    }
}