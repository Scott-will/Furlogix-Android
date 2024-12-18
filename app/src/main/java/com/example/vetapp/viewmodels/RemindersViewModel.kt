package com.example.vetapp.viewmodels

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.Entities.Reminder
import com.example.vetapp.VetApplication
import com.example.vetapp.broadcastreceivers.FillOutReportsNotificationReceiver
import com.example.vetapp.broadcastreceivers.SendReportsNotificationReceiver
import com.example.vetapp.repositories.RemindersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val remindersRepository: RemindersRepository
) : ViewModel() {

    private val TAG = "VetApp:" + ReportViewModel::class.qualifiedName

    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")

    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if(alarmManager.canScheduleExactAlarms()){
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                    }
                }
                else{
                    //TODO how to do it for old api versions
                    return
                }

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

    fun checkAndRequestExactAlarmPermission() : Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = VetApplication.applicationContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            return alarmManager.canScheduleExactAlarms()
        }
        //todo old api versions
        else{
            return false;
        }
    }

    fun getAllReminders(){
        remindersRepository.getAllReminders()
    }

    fun insertReminder(reminder: Reminder){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = remindersRepository.insertReminder(reminder)
                UpdateErrorState(!result.result, result.msg)
            }
        }
    }

    fun UpdateErrorState(isError : Boolean, errorMsg : String){
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }
}