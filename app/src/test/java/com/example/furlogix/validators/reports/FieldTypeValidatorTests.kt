package com.furlogix.validators.reports

import com.furlogix.reports.FieldType
import com.furlogix.reports.FieldTypeValidator
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FieldTypeValidatorTests {

    @Test
    fun `should return true for TEXT type with any value`() = runTest{
        // Given
        val type = FieldType.TEXT
        val value = "Any text"

        // When
        val result = FieldTypeValidator.validateFieldTypeWithValue(type, value)

        // Then
        assertTrue(result)
    }

    @Test
    fun `should return true for BOOLEAN type with valid boolean value`() = runTest {
        // Given
        val type = FieldType.CHECKBOX

        // When
        val resultTrue = FieldTypeValidator.validateFieldTypeWithValue(type, "true")
        val resultFalse = FieldTypeValidator.validateFieldTypeWithValue(type, "false")

        // Then
        assertTrue(resultTrue)
        assertTrue(resultFalse)
    }

    @Test
    fun `should return false for BOOLEAN type with invalid boolean value`() = runTest{
        // Given
        val type = FieldType.CHECKBOX

        // When
        val result = FieldTypeValidator.validateFieldTypeWithValue(type, "invalidBoolean")

        // Then
        assertFalse(result)
    }

    @Test
    fun `should return true for NUMBER type with valid numeric value`() = runTest{
        // Given
        val type = FieldType.NUMBER

        // When
        val resultInt = FieldTypeValidator.validateFieldTypeWithValue(type, "123")
        val resultDouble = FieldTypeValidator.validateFieldTypeWithValue(type, "123.45")

        // Then
        assertTrue(resultInt)
        assertTrue(resultDouble)
    }

    @Test
    fun `should return false for NUMBER type with invalid numeric value`() = runTest {
        // Given
        val type = FieldType.NUMBER

        // When
        val result = FieldTypeValidator.validateFieldTypeWithValue(type, "invalidNumber")

        // Then
        assertFalse(result)
    }
}
