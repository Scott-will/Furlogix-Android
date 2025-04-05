package com.furlogix.viewmodels

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furlogix.Database.Entities.Reminder
import com.furlogix.Furlogix
import com.furlogix.broadcastreceivers.NotificationReceiver
import com.furlogix.reminders.RequestCodeFactory
import com.furlogix.repositories.IRemindersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val remindersRepository: IRemindersRepository
) : ViewModel() {

    private val TAG = "Furlogix:" + ReportViewModel::class.qualifiedName
    val reminders = remindersRepository.getAllReminders().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")

    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg

    public fun scheduleReminder(date : String, time : String, recurrence : String, title: String, message : String){
        Log.d(TAG, "Scheduling reminder")
        val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val context = Furlogix.applicationContext()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val requestCode = RequestCodeFactory.GetRequestCode()
        val pendingIntent = BuildReminderPendingIntent(context, title, message, requestCode)
        try {
            // Combine the date and time into a single string for parsing
            val dateTimeString = "$date $time"
            val dateTime = dateTimeFormat.parse(dateTimeString)
            calendar.time = dateTime
        } catch (e: Exception) {
            Log.e(TAG, "Exception scheduling reminder: ${e.printStackTrace()}")
            return
        }
        when (recurrence) {
            "Once" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if(alarmManager.canScheduleExactAlarms()){
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                    }
                    else{
                        Log.e(TAG, "Dont have permissions to schedule exact alerms")
                        return
                    }
                }
                else{
                    Log.e(TAG, "Exception scheduling reminder:")
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
        Log.d(TAG, "Set a reminder for ${title}")
        val reminder = Reminder(type = title, frequency = recurrence, startTime = time, requestCode = requestCode, title = title, message = message)
        insertReminder(reminder)
        Log.d(TAG, "Saved reminder to database")
    }

    fun BuildReminderPendingIntent(context: Context, title : String, message : String, requestCode : Int) : PendingIntent{
        var intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("notification_title", title)
        intent.putExtra("notification_message", message)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        return pendingIntent
    }

    fun checkAndRequestExactAlarmPermission() : Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = Furlogix.applicationContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            return alarmManager.canScheduleExactAlarms()
        }
        //todo old api versions
        else{
            return false;
        }
    }

    fun insertReminder(reminder: Reminder){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = remindersRepository.insertReminder(reminder)
                UpdateErrorState(!result.result, result.msg)
            }
        }
    }

    fun deleteReminder(reminder: Reminder){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                var result = cancelAlarmForReminder(reminder)
                if(!result){
                    return@withContext
                }
                try{
                    remindersRepository.deleteReminder(reminder)
                }
                catch(e : Exception){
                    Log.e(TAG, "Failed to delete notification from database ${e.message}")
                }
            }
        }
    }

    fun cancelAlarmForReminder(reminder: Reminder) : Boolean{
        try{
            val context = Furlogix.applicationContext()
            val intent = Intent(context, NotificationReceiver::class.java)
            intent.putExtra("notification_title", reminder.title)
            intent.putExtra("notification_message", reminder.message)
            val pendingIntent = PendingIntent.getBroadcast(context, reminder.requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }
        catch(e : Exception){
            Log.e(TAG, "Failed to delete alarm for notification ${e.message}")
            return false
        }
        return true
    }

    fun UpdateErrorState(isError : Boolean, errorMsg : String){
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }
}