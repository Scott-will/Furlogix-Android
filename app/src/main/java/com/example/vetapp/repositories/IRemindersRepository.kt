package com.example.vetapp.repositories

import com.example.vetapp.Database.Entities.Reminder
import com.example.vetapp.Result
import kotlinx.coroutines.flow.Flow

interface IRemindersRepository {

    fun getAllReminders() : Flow<Reminder>

    suspend fun insertReminder(reminder : Reminder) : Result
}