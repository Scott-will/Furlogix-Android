package com.furlogix.repositories

import com.furlogix.Database.DAO.RemindersDao
import com.furlogix.Database.Entities.Reminder
import com.furlogix.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemindersRepository @Inject constructor(
    private val remindersDao: RemindersDao,
    //validator
) : IRemindersRepository{

    override fun getAllReminders(): Flow<List<Reminder>> {
       return remindersDao.getAllRemindersFlow()
    }

    override suspend fun insertReminder(reminder: Reminder): Result {
        //validate
        remindersDao.insert(reminder)
        return Result(true, "Reminder added to database successfully")
    }

    override suspend fun deleteReminder(reminder: Reminder): Result {
        //validate
        remindersDao.delete(reminder)
        return Result(true, "Reminder added to database successfully")
    }
}