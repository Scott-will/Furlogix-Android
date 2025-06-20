package com.furlogix.viewmodels

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furlogix.Database.Entities.Reminder
import com.furlogix.Furlogix
import com.furlogix.broadcastreceivers.NotificationReceiver
import com.furlogix.logger.ILogger
import com.furlogix.reminders.RequestCodeFactory
import com.furlogix.repositories.IRemindersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val logger : ILogger,
    private val remindersRepository: IRemindersRepository,
    private val requestCodeFactory: RequestCodeFactory,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val TAG = "Furlogix:" + ReportViewModel::class.qualifiedName
    val reminders = remindersRepository.getAllReminders().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var _isError = MutableStateFlow<Boolean>(false)
    private var _errorMsg = MutableStateFlow<String>("")

    var isError : StateFlow<Boolean> = _isError
    var errorMsg : StateFlow<String> = _errorMsg

    @SuppressLint("NewApi")
    public fun scheduleReminder(dateTime : LocalDateTime, recurrence : String, title: String, message : String){
        logger.log(TAG, "Scheduling reminder")
        val calendar = Calendar.getInstance()
        val context = Furlogix.applicationContext()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val requestCode = this.requestCodeFactory.getRequestCode()
        val pendingIntent = BuildReminderPendingIntent(context, title, message, requestCode)
        if(pendingIntent == null){
            return
        }
        try {
            val zoneId = ZoneId.systemDefault()
            val instant = dateTime.atZone(zoneId).toInstant()
            calendar.time =  Date.from(instant)
        } catch (e: Exception) {
            logger.logError(TAG, "Exception scheduling reminder:", e)
            return
        }
        when (recurrence) {
            "Once" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if(alarmManager.canScheduleExactAlarms()){
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                    }
                    else{
                        logger.logError(TAG, "Dont have permissions to schedule exact alerms", null)
                        return
                    }
                }
                else{
                    logger.logError(TAG, "Exception scheduling reminder:", null)
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
        logger.log(TAG, "Set a reminder for ${title}")

        val reminder = Reminder(type = title, frequency = recurrence, startTime = calendar.time.toString()
            , requestCode = requestCode, title = title, message = message)
        insertReminder(reminder)
        logger.log(TAG, "Saved reminder to database")
    }

    fun BuildReminderPendingIntent(context: Context, title : String, message : String, requestCode : Int) : PendingIntent? {
        if (title.isBlank()) {
            _isError.value = true
            _errorMsg.value = "Please provide a title"
            return null
        }
        if (message.isBlank()) {
            _isError.value = true
            _errorMsg.value = "Please provide a message"
            return null
        }
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
            withContext(ioDispatcher){
                val result = remindersRepository.insertReminder(reminder)
                UpdateErrorState(!result.result, result.msg)
            }
        }
    }

    fun deleteReminder(reminder: Reminder){
        viewModelScope.launch {
            withContext(ioDispatcher){
                var result = cancelAlarmForReminder(reminder)
                if(!result){
                    return@withContext
                }
                try{
                    remindersRepository.deleteReminder(reminder)
                }
                catch(e : Exception){
                    logger.logError(TAG, "Failed to delete notification from database ${e.message}", e)
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
            logger.logError(TAG, "Failed to delete alarm for notification ${e.message}", e)
            return false
        }
        return true
    }

    fun UpdateErrorState(isError : Boolean, errorMsg : String){
        this._isError.value = isError
        this._errorMsg.value = errorMsg

    }
}