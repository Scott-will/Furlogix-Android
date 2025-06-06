package com.example.furlogix

import com.furlogix.Database.AppDatabase
import com.furlogix.Database.DAO.RemindersDao
import com.furlogix.Database.Entities.Reminder
import com.furlogix.reminders.RequestCodeFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RequestCodeFactoryTests {
    private val mockDatabase = mockk<AppDatabase>(relaxed = true)
    private val mockDao = mockk<RemindersDao>(relaxed = true)
    private lateinit var factory: RequestCodeFactory
    private val mockReminder = Reminder(1, "", "", "", requestCode = 42, "", "")

    @Before
    fun setup() {
        every { mockDatabase.remindersDao() } returns mockDao
        factory = RequestCodeFactory(mockDatabase)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun InitFromDbCorrectly_DbHasNoValue(){
        every { mockDao.getLargestRequestCode() } returns null
        factory.initRequestCode()
        val next = factory.getRequestCode()
        Assert.assertEquals(2, next)
    }
    @Test
    fun InitFromDbCorrectly_DbHasValue(){
        every { mockDao.getLargestRequestCode() } returns mockReminder
        factory.initRequestCode()
        val next = factory.getRequestCode()
        Assert.assertEquals(mockReminder.requestCode + 1, next)
    }

    @Test
    fun FactoryRollsOverAtMaxInt(){
        mockReminder.requestCode = Int.MAX_VALUE
        every { mockDao.getLargestRequestCode() } returns mockReminder
        factory.initRequestCode()
        val next = factory.getRequestCode()
        Assert.assertEquals(1, next)
    }

    @Test
    fun GetRequestCodeIncrements(){
        val code1 = factory.getRequestCode()
        val code2 = factory.getRequestCode()
        Assert.assertEquals(code1 + 1, code2)
    }

}