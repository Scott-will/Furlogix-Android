package com.example.vetapp.validators.reports

import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.reports.FieldType
import com.example.vetapp.reports.ReportTemplateValidator
import com.example.vetapp.repositories.IReportsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Before


class ReportTemplateValidatorTest {

    private lateinit var reportTemplateValidator: ReportTemplateValidator

    @Before
    fun setUp() {
        reportTemplateValidator = ReportTemplateValidator()
    }

    @Test
    fun `should return true for valid template with existing report and non-empty name`() =
        runTest {
            // Given
            val template = ReportTemplateField(
                reportId = 1,
                name = "Valid Template",
                fieldType = FieldType.NUMBER
            )

            // When
            val result = reportTemplateValidator.ValidateTemplate(template)

            // Then
            assertTrue(result)
        }

    @Test
    fun `should return false when template name is empty`() = runTest {
        // Given
        val template = ReportTemplateField(reportId = 1, name = "", fieldType = FieldType.NUMBER)
        val result = reportTemplateValidator.ValidateTemplate(template)

        // Then
        assertFalse(result)
    }
}

