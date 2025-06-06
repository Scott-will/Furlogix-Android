package com.example.furlogix.viewModels

import com.furlogix.logger.ILogger
import com.furlogix.repositories.IReportEntryRepository
import com.furlogix.viewmodels.ReportEntryHistoryViewModel
import com.furlogix.viewmodels.ReportEntryViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
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

class ReportEntryHistoryViewModelTests {

    private lateinit var viewModel : ReportEntryHistoryViewModel
    private lateinit var reportEntryRepository: IReportEntryRepository

    @Before
    fun setup(){
        val logger = mockk<ILogger>()
        every { logger.log(any(), any()) } just Runs
        every { logger.logError(any(), any(), any()) } just Runs

        reportEntryRepository = mockk()
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = ReportEntryHistoryViewModel(logger, reportEntryRepository, testDispatcher)
    }

    @After
    fun tearDown(){
        unmockkObject(reportEntryRepository)
        Dispatchers.resetMain()
    }

    @Test
    fun deleteEntry_Success() = runTest {
        coEvery { reportEntryRepository.deleteReportEntry(any()) } just runs
        viewModel.DeleteReportEntry(1)
        advanceUntilIdle()
        Assert.assertFalse(viewModel.isError.value)
        Assert.assertEquals("", viewModel.errorMsg.value)
    }

    @Test
    fun deleteEntry_Exception() = runTest {
        coEvery { reportEntryRepository.deleteReportEntry(any()) } throws RuntimeException("Error")
        viewModel.DeleteReportEntry(1)
        advanceUntilIdle()
        Assert.assertTrue(viewModel.isError.value)
        Assert.assertNotEquals("", viewModel.errorMsg.value)
    }
}