package com.furlogix.validators.reports

import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.Database.Entities.Reports
import com.furlogix.reports.FieldType
import com.furlogix.reports.ReportEntryValidator
import com.furlogix.repositories.IReportTemplateRepository
import com.furlogix.repositories.IReportsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ReportEntryValidatorTests {

    private lateinit var reportEntryValidator: ReportEntryValidator
    private val reportRepository: IReportsRepository = mockk()
    private val reportTemplateRepository: IReportTemplateRepository = mockk()

    @Before
    fun setUp() {
        reportEntryValidator = ReportEntryValidator(reportTemplateRepository, reportRepository)
    }

    @Test
    fun `should return true for valid entry with valid report and template`() = runTest {
        // Given
        val entry = ReportEntry(reportId = 1, templateId = 1, value = "Valid Value", timestamp = "")
        val mockReport = mockk<Reports>() // Mock the Report object
        val mockTemplate = ReportTemplateField(uid = 1, reportId = 1, fieldType = FieldType.TEXT, name = "test", icon="test")


        coEvery { reportRepository.getReportById(entry.reportId) } returns mockReport
        coEvery { reportTemplateRepository.GetTemplateById(entry.templateId) } returns mockTemplate

        // When
        val result = reportEntryValidator.ValidateEntry(entry)

        // Then
        assertTrue(result.result)
        coVerify { reportRepository.getReportById(entry.reportId) }
        coVerify { reportTemplateRepository.GetTemplateById(entry.templateId) }
    }

    @Test
    fun `should return false when report does not exist`() = runTest {
        // Given
        val entry = ReportEntry(reportId = -1, templateId = 1, value = "Valid Value", timestamp = "")
        coEvery { reportRepository.getReportById(entry.reportId) } throws Exception("Report not found")

        // When
        val result = reportEntryValidator.ValidateEntry(entry)

        // Then
        assertFalse(result.result)
        coVerify { reportRepository.getReportById(entry.reportId) }
    }

    @Test
    fun `should return false when template does not exist`() = runTest {
        // Given
        val entry = ReportEntry(reportId = 1, templateId = -1, value = "Valid Value", timestamp = "")
        coEvery { reportRepository.getReportById(entry.reportId) } returns mockk()
        coEvery { reportTemplateRepository.GetTemplateById(entry.templateId) } throws Exception("Template not found")
        // When
        val result = reportEntryValidator.ValidateEntry(entry)

        // Then
        assertFalse(result.result)
        coVerify { reportRepository.getReportById(entry.reportId) }
        coVerify { reportTemplateRepository.GetTemplateById(entry.templateId) }
    }

    @Test
    fun `should return false when field type validation fails`() = runTest {
        // Given
        val entry = ReportEntry(reportId = 1, templateId = 1, value = "Invalid Value", timestamp = "")
        val mockTemplate = ReportTemplateField(reportId = 1, fieldType = FieldType.NUMBER, name = "test", icon="test")

        coEvery { reportRepository.getReportById(entry.reportId) } returns mockk()
        coEvery { reportTemplateRepository.GetTemplateById(entry.templateId) } returns mockTemplate

        // When
        val result = reportEntryValidator.ValidateEntry(entry)

        // Then
        assertFalse(result.result)
        coVerify { reportRepository.getReportById(entry.reportId) }
        coVerify { reportTemplateRepository.GetTemplateById(entry.templateId) }
    }
}
