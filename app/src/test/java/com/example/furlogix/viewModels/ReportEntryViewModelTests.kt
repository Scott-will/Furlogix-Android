package com.example.furlogix.viewModels

import androidx.compose.runtime.mutableStateOf
import com.furlogix.Result
import com.furlogix.logger.ILogger
import com.furlogix.repositories.IReportEntryRepository
import com.furlogix.viewmodels.ReportEntryViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ReportEntryViewModelTests {

    private lateinit var viewModel : ReportEntryViewModel
    private lateinit var reportEntryRepository: IReportEntryRepository

    @Before
    fun setup(){
        val logger = mockk<ILogger>()
        every { logger.log(any(), any()) } just Runs
        every { logger.logError(any(), any(), any()) } just Runs

        reportEntryRepository = mockk()
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = ReportEntryViewModel(logger, reportEntryRepository, testDispatcher)
    }

    @After
    fun tearDown(){
        unmockkObject(reportEntryRepository)
        Dispatchers.resetMain()
    }

    @Test
    fun insertReportEntry_Success() = runTest {
        val valueMap = mapOf(
            1 to mutableStateOf("Value 1"),
            2 to mutableStateOf("Value 2")
        )
        val reportId = 42
        val timestamp = "2025-06-06T12:00:00Z"
        val expectedResult = Result(true, "Success")

        coEvery { reportEntryRepository.insertEntries(any()) } returns expectedResult

        viewModel.insertReportEntry(valueMap, reportId, timestamp)
        advanceUntilIdle()

        Assert.assertFalse(viewModel.isError.value)
        Assert.assertEquals("", viewModel.errorMsg.value)
    }

    @Test
    fun insertReportEntry_Exception_DoesntCrash() = runTest{
        val valueMap = mapOf(
            1 to mutableStateOf("Value 1"),
            2 to mutableStateOf("Value 2")
        )
        val reportId = 42
        val timestamp = "2025-06-06T12:00:00Z"

        coEvery { reportEntryRepository.insertEntries(any()) } throws RuntimeException("Error")

        viewModel.insertReportEntry(valueMap, reportId, timestamp)
        advanceUntilIdle()

        Assert.assertTrue(viewModel.isError.value)
        Assert.assertNotEquals("", viewModel.errorMsg.value)
    }
}
