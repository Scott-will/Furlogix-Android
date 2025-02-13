package com.example.vetapp.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.RemindersDao
import com.example.vetapp.Database.Entities.Reminder
import com.example.vetapp.R
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ReminderDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var reminderDao: RemindersDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.deleteDatabase(R.string.database_name.toString())
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        reminderDao = db.remindersDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndRetrieveReports() = runBlocking {
        val reminder = Reminder(frequency = "Once", type = "Send", startTime = "abcd")
        reminderDao.insert(reminder)
        val reminders = reminderDao.getAllRemindersFlow().first()
        assertEquals(reminders.count(), 1)
        assertEquals(reminders.first().type, "Send")
    }

    @Test
    fun deleteReports() = runBlocking {
        val reminder = Reminder(frequency = "Once", type = "Send", startTime = "abcd")
        reminderDao.insert(reminder)
        val insertedReports = reminderDao.getAllRemindersFlow().first().first()
        assertNotNull(insertedReports)
        reminderDao.delete(insertedReports)

        val result = reminderDao.getAllRemindersFlow().firstOrNull()
        assert(result!!.isEmpty())
    }
}