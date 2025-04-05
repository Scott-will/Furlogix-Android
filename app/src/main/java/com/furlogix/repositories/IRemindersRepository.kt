package com.furlogix.repositories

import com.furlogix.Database.Entities.Reminder
import com.furlogix.Result
import kotlinx.coroutines.flow.Flow

interface IRemindersRepository {

    fun getAllReminders() : Flow<List<Reminder>>

    suspend fun insertReminder(reminder : Reminder) : Result

    suspend fun deleteReminder(reminder : Reminder) : Result
}