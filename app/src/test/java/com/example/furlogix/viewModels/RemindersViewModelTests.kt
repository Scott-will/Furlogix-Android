package com.example.furlogix.viewModels

import android.content.Context
import com.furlogix.Database.Entities.Reminder
import com.furlogix.Result
import com.furlogix.logger.ILogger
import com.furlogix.reminders.RequestCodeFactory
import com.furlogix.repositories.IRemindersRepository
import com.furlogix.viewmodels.RemindersViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RemindersViewModelTests {
    private lateinit var viewModel : RemindersViewModel
    private lateinit var logger : ILogger
    private lateinit var remindersRepository: IRemindersRepository
    private lateinit var context : Context
    private val testDispatcher = StandardTestDispatcher()
    @Before
    fun setup(){
        Dispatchers.setMain(this.testDispatcher)

        remindersRepository = mockk()
        every {remindersRepository.getAllReminders()} returns flowOf()
        logger = mockk()
        context = mockk(relaxed = true)
        every { logger.log(any(), any()) } just Runs
        every { logger.logError(any(), any(), any()) } just Runs
        var requestCodeFactory : RequestCodeFactory = mockk()

        viewModel = RemindersViewModel(logger, remindersRepository, requestCodeFactory, testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkObject(logger)
        unmockkObject(remindersRepository)
        Dispatchers.resetMain()
    }

    //build intent success/fail
    @Test
    fun BuildReminderPendingIntent_EmptyTitle_Fails(){
        viewModel.BuildReminderPendingIntent(context, "", "some text", 1)
        Assert.assertTrue(viewModel.isError.value)
        Assert.assertNotEquals("", viewModel.errorMsg)
    }

    @Test
    fun BuildReminderPendingIntent_EmptyMessage_Fails(){
        viewModel.BuildReminderPendingIntent(context, "title", "", 1)
        Assert.assertTrue(viewModel.isError.value)
        Assert.assertNotEquals("", viewModel.errorMsg)
    }

    @Test
    fun InsertReminder_Error_ShowsError() = runTest{
        val reminder = Reminder(1, "Daily", "sent", "08:00", 1, "title", "message")
        coEvery { remindersRepository.insertReminder(reminder) } returns Result(false, "Some Error")
        viewModel.insertReminder(reminder)
        advanceUntilIdle()

        Assert.assertTrue(viewModel.isError.value)
        Assert.assertNotEquals("", viewModel.errorMsg)
    }

    @Test
    fun InsertReminder_Success_DoesNotShowError() = runTest{
        val reminder = Reminder(1, "Daily", "sent", "08:00", 1, "title", "message")
        coEvery { remindersRepository.insertReminder(reminder) } returns Result(true, "")
        viewModel.insertReminder(reminder)
        advanceUntilIdle()

        Assert.assertFalse(viewModel.isError.value)
    }

}