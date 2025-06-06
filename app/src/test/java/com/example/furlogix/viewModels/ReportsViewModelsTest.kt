package com.example.furlogix.viewModels

import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Database.Entities.Reports
import com.furlogix.Result
import com.furlogix.logger.ILogger
import com.furlogix.repositories.IReportEntryRepository
import com.furlogix.repositories.IReportTemplateRepository
import com.furlogix.repositories.IReportsRepository
import com.furlogix.repositories.IUserRepository
import com.furlogix.ui.navigation.Screen
import com.furlogix.viewmodels.ReportViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ReportsViewModelsTest {

    private lateinit var viewmodel : ReportViewModel
    private lateinit var logger : ILogger
    private lateinit var reportTemplateRepository: IReportTemplateRepository
    private lateinit var reportRepository: IReportsRepository
    private lateinit var userRepository : IUserRepository
    private lateinit var reportEntryRepository : IReportEntryRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(this.testDispatcher)

        logger = mockk()
        every { logger.log(any(), any()) } just Runs
        every { logger.logError(any(), any(), any()) } just Runs

        this.reportRepository = mockk()
        every { reportRepository.reportsObservable() } returns flowOf()

        this.reportEntryRepository = mockk()
        coEvery { reportEntryRepository.getSizeOfReportEntryTableKB() } returns 0

        this.reportTemplateRepository = mockk()
        this.userRepository = mockk()

        viewmodel = ReportViewModel(logger, reportTemplateRepository, reportRepository,
            userRepository, reportEntryRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkObject(this.reportRepository)
        unmockkObject(this.reportRepository)
        unmockkObject(this.reportTemplateRepository)
        unmockkObject(this.userRepository)
        unmockkObject(logger)
    }

    @Test
    fun UpdateReport_Valid_Success() = runTest{
        val testReport = Reports(1, "test", 1)
        coEvery { reportRepository.updateReport(testReport) } returns Result(true, "")
        viewmodel.updateReport(testReport)
        advanceUntilIdle()
        Assert.assertFalse(viewmodel.isError.value)
        Assert.assertEquals("", viewmodel.errorMsg.value)
    }

    @Test
    fun UpdateReport_Valid_Fails() = runTest{
        val testReport = Reports(1, "test", 1)
        coEvery { reportRepository.updateReport(testReport) } returns Result(false, "error")
        viewmodel.updateReport(testReport)
        advanceUntilIdle()
        Assert.assertTrue(viewmodel.isError.value)
        Assert.assertNotEquals("", viewmodel.errorMsg.value)
    }

    @Test
    fun UpdateReport_Valid_Exception_Fails() = runTest{
        val testReport = Reports(1, "test", 1)
        coEvery { reportRepository.updateReport(testReport) } throws RuntimeException("Error to update")
        viewmodel.updateReport(testReport)
        advanceUntilIdle()
        Assert.assertTrue(viewmodel.isError.value)
        Assert.assertNotEquals("", viewmodel.errorMsg.value)
    }

    @Test
    fun InsertReport_Valid_Success() = runTest{
        coEvery { reportRepository.insertReport(any()) } returns Result(true, "")
        viewmodel.insertReport("test", 1)
        advanceUntilIdle()
        Assert.assertFalse(viewmodel.isError.value)
        Assert.assertEquals("", viewmodel.errorMsg.value)
    }

    @Test
    fun InsertReport_Valid_Fails() = runTest{
        coEvery { reportRepository.insertReport(any()) } returns Result(false, "some error")
        viewmodel.insertReport("test", 1)
        advanceUntilIdle()
        Assert.assertTrue(viewmodel.isError.value)
        Assert.assertNotEquals("", viewmodel.errorMsg.value)
    }

    @Test
    fun InsertReport_Valid_Exceptions_Fails() = runTest{
        val testReport = Reports(1, "test", 1)
        coEvery { reportRepository.insertReport(testReport) } throws RuntimeException("Error to update")
        viewmodel.updateReport(testReport)
        advanceUntilIdle()
        Assert.assertTrue(viewmodel.isError.value)
        Assert.assertNotEquals("", viewmodel.errorMsg.value)
    }

    @Test
    fun GatherReportData_RetrieveData_Fails() = runTest {
        coEvery {   reportRepository.getReportById(1) } throws RuntimeException("failed to get data")
        viewmodel.gatherReportData(1)
        advanceUntilIdle()
        Assert.assertEquals(true, viewmodel.isError.value)
        Assert.assertNotEquals("", viewmodel.errorMsg.value)
    }

    @Test
    fun PopulateReportEntries_Success_DataFormatted_Correctly() = runTest {
        val reportEntryList = mutableListOf<ReportEntry>()
        for (i in 1..5){
            val reportEntry = ReportEntry(i, "${i}", 1, 1, "now")
            reportEntryList.add(reportEntry)
        }
        coEvery { reportEntryRepository.getAllReportEntriesForTemplate(1) } returns flowOf(reportEntryList)
        viewmodel.PopulateReportEntriesProperty(1)
        advanceUntilIdle()
        Assert.assertEquals(5, viewmodel.reportEntries.value[1]?.size ?: 0)
    }
    @Test
    fun checkTooManyReportEntries_TooMany_Success() = runTest{
        coEvery { reportEntryRepository.getSizeOfReportEntryTableKB() } returns 101
        viewmodel = ReportViewModel(logger, reportTemplateRepository, reportRepository,
            userRepository, reportEntryRepository, testDispatcher)
        advanceUntilIdle()
        Assert.assertTrue(viewmodel.isTooManyReports.value)
    }

    @Test
    fun checkTooManyReportEntries_LessThan_Success() = runTest{
        coEvery { reportEntryRepository.getSizeOfReportEntryTableKB() } returns 50
        viewmodel = ReportViewModel(logger, reportTemplateRepository, reportRepository,
            userRepository, reportEntryRepository, testDispatcher)
        advanceUntilIdle()
        Assert.assertFalse(viewmodel.isTooManyReports.value)
    }

    @Test
    fun checkTooManyReportEntries_Exception_Ok() = runTest{
        coEvery { reportEntryRepository.getSizeOfReportEntryTableKB() } throws RuntimeException("Failed to get data")
        viewmodel = ReportViewModel(logger, reportTemplateRepository, reportRepository,
            userRepository, reportEntryRepository, testDispatcher)
        advanceUntilIdle()
        Assert.assertFalse(viewmodel.isTooManyReports.value)
    }
        //populate report entries
            //valid invalid error

}