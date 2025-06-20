package com.furlogix.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.furlogix.Database.AppDatabase
import com.furlogix.Database.DAO.RemindersDao
import com.furlogix.Database.Entities.Reminder
import com.furlogix.R
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
        val reminder = Reminder(frequency = "Once", type = "Send", startTime = "abcd", requestCode = 1, title = "test", message = "test")
        reminderDao.insert(reminder)
        val reminders = reminderDao.getAllRemindersFlow().first()
        assertEquals(reminders.count(), 1)
        assertEquals(reminders.first().type, "Send")
    }

    @Test
    fun deleteReports() = runBlocking {
        val reminder = Reminder(frequency = "Once", type = "Send", startTime = "abcd", requestCode = 1, title = "test", message = "test")
        reminderDao.insert(reminder)
        val insertedReports = reminderDao.getAllRemindersFlow().first().first()
        assertNotNull(insertedReports)
        reminderDao.delete(insertedReports)

        val result = reminderDao.getAllRemindersFlow().firstOrNull()
        assert(result!!.isEmpty())
    }

    @Test
    fun GetLargestRequestCode() = runBlocking{
        val reminder1 = Reminder(frequency = "Once",
            type="Send",
            startTime = "abcd",
            requestCode = 987,
            title="test",
            message="test")
        val reminder2 = Reminder(frequency = "Once",
            type="Send",
            startTime = "abcd",
            requestCode = 1001,
            title="test2",
            message="test")
        val reminder3 = Reminder(frequency = "Once",
            type="Send",
            startTime = "abcd",
            requestCode = 105,
            title="test3",
            message="test")
        reminderDao.insert(reminder1)
        reminderDao.insert(reminder2)
        reminderDao.insert(reminder3)
        val reminderLargest = reminderDao.getLargestRequestCode()
        assert(reminderLargest?.title == "test2")
        assert(reminderLargest?.requestCode == 1001)
    }
}