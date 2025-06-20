package com.example.furlogix.viewModels

import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.Result
import com.furlogix.logger.ILogger
import com.furlogix.reports.FieldType
import com.furlogix.repositories.IReportTemplateRepository
import com.furlogix.viewmodels.ReportTemplatesViewModels
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

class ReportTemplateViewModelTests {
    private lateinit var reportTemplateRepository: IReportTemplateRepository
    private lateinit var viewModel : ReportTemplatesViewModels

    @Before
    fun setup(){
        val logger = mockk<ILogger>()
        every { logger.log(any(), any()) } just Runs
        every { logger.logError(any(), any(), any()) } just Runs

        reportTemplateRepository = mockk()
        every { reportTemplateRepository.ReportTemplateObservable() } returns flowOf()
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        viewModel = ReportTemplatesViewModels(logger, reportTemplateRepository, testDispatcher)
    }

    @After
    fun tearDown(){
        unmockkObject(reportTemplateRepository)
    }

    @Test
    fun PopulateCurrentReportTemplate_Success() = runTest{
        val reportTemplate = ReportTemplateField(1,
            1,
            FieldType.TEXT,
            "test",
            false,
            "")
        coEvery { reportTemplateRepository.GetTemplateById(1) } returns reportTemplate
        viewModel.populateCurrentReportTemplate(1)
        advanceUntilIdle()
        Assert.assertEquals(reportTemplate, viewModel.currentReportTemplate.value)
    }

    @Test
    fun PopulateCurrentReportTemplate_Exception_DoesntCrash() = runTest{
        coEvery { reportTemplateRepository.GetTemplateById(1) } throws RuntimeException("Error")
        viewModel.populateCurrentReportTemplate(1)
        advanceUntilIdle()
        Assert.assertEquals(true, viewModel.isError.value)
    }

    @Test
    fun PopulateTemplatesForReport_Success() = runTest{
        val reportTemplate = ReportTemplateField(1,
            1,
            FieldType.TEXT,
            "test",
            false,
            "")
        var mockList = listOf(reportTemplate)
        coEvery { reportTemplateRepository.GetReportById(1) } returns mockList
        viewModel.populateReportTemplatesForCurrentReport(1)
        advanceUntilIdle()
        Assert.assertEquals(mockList, viewModel.reportTemplatesForCurrentReport.value)
    }

    @Test
    fun PopulateTemplatesForReport_Exception_DoesntCrash() = runTest{
        coEvery { reportTemplateRepository.GetReportById(1) } throws RuntimeException("Error")
        viewModel.populateReportTemplatesForCurrentReport(1)
        advanceUntilIdle()
        Assert.assertTrue(viewModel.isError.value)
    }

    @Test
    fun InsertTemplate_Valid_Success() = runTest{
        val reportTemplate = ReportTemplateField(1,
            1,
            FieldType.TEXT,
            "test",
            false,
            "")
        coEvery { reportTemplateRepository.insertReportTemplateField(reportTemplate) } returns Result(true, "")
        viewModel.insertReportTemplateField(reportTemplate)
        advanceUntilIdle()
        Assert.assertFalse(viewModel.isError.value)
        Assert.assertEquals("", viewModel.errorMsg.value)
    }

    @Test
    fun InsertTemplate_Valid_Fails() = runTest{
        val reportTemplate = ReportTemplateField(1,
            1,
            FieldType.TEXT,
            "test",
            false,
            "")
        coEvery { reportTemplateRepository.insertReportTemplateField(reportTemplate) } returns Result(false, "error")
        viewModel.insertReportTemplateField(reportTemplate)
        advanceUntilIdle()
        Assert.assertTrue(viewModel.isError.value)
        Assert.assertNotEquals("", viewModel.errorMsg.value)
    }

    @Test
    fun InsertTemplate_Valid_Exception() =  runTest{
        val reportTemplate = ReportTemplateField(1,
            1,
            FieldType.TEXT,
            "test",
            false,
            "")
        coEvery { reportTemplateRepository.insertReportTemplateField(reportTemplate) } throws RuntimeException("Error")
        viewModel.insertReportTemplateField(reportTemplate)
        advanceUntilIdle()
        Assert.assertTrue(viewModel.isError.value)
        Assert.assertNotEquals("", viewModel.errorMsg.value)
    }

    @Test
    fun UpdateTemplate_Valid_Success() = runTest{
        val reportTemplate = ReportTemplateField(1,
            1,
            FieldType.TEXT,
            "test",
            false,
            "")
        coEvery { reportTemplateRepository.updateReportTemplateField(reportTemplate) } returns Result(true, "")
        viewModel.updateReportTemplateField(reportTemplate)
        advanceUntilIdle()
        Assert.assertFalse(viewModel.isError.value)
        Assert.assertEquals("", viewModel.errorMsg.value)
    }

    @Test
    fun UpdateTemplate_Valid_Fails() = runTest{
        val reportTemplate = ReportTemplateField(1,
            1,
            FieldType.TEXT,
            "test",
            false,
            "")
        coEvery { reportTemplateRepository.updateReportTemplateField(reportTemplate) } returns Result(false, "error")
        viewModel.updateReportTemplateField(reportTemplate)
        advanceUntilIdle()
        Assert.assertTrue(viewModel.isError.value)
        Assert.assertNotEquals("", viewModel.errorMsg.value)
    }

    @Test
    fun UpdateTemplate_Valid_Exception() = runTest{
        val reportTemplate = ReportTemplateField(1,
            1,
            FieldType.TEXT,
            "test",
            false,
            "")
        coEvery { reportTemplateRepository.updateReportTemplateField(reportTemplate) } throws RuntimeException("Error")
        viewModel.updateReportTemplateField(reportTemplate)
        advanceUntilIdle()
        Assert.assertTrue(viewModel.isError.value)
        Assert.assertNotEquals("", viewModel.errorMsg.value)
    }

}