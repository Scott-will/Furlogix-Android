package com.example.vetapp.validators.reports

import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.reports.ReportValidator
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ReportValidatorTest {

    private val reportValidator = ReportValidator()

    @Test
    fun `should return true for a valid report with a non-empty name`() = runTest {
        // Given
        val report = Reports(name = "Valid Report", petId = 1)

        // When
        val result = reportValidator.ValidateReport(report)

        // Then
        assertTrue(result.result)
    }

    @Test
    fun `should return false for an invalid report with an empty name`() = runTest {
        // Given
        val report = Reports(name = "", petId = 1)

        // When
        val result = reportValidator.ValidateReport(report)

        // Then
        assertFalse(result.result)
    }
}
