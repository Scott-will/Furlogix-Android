package com.furlogix.validators.reports

import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.reports.FieldType
import com.furlogix.reports.ReportTemplateValidator
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


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
                fieldType = FieldType.NUMBER,
                icon="test",
                units = ""
            )

            // When
            val result = reportTemplateValidator.ValidateTemplate(template)

            // Then
            assertTrue(result.result)
        }

    @Test
    fun `should return false when template name is empty`() = runTest {
        // Given
        val template = ReportTemplateField(reportId = 1,
            name = "",
            fieldType = FieldType.NUMBER,
            icon="test",
            units = "")
        val result = reportTemplateValidator.ValidateTemplate(template)

        // Then
        assertFalse(result.result)
    }
}

