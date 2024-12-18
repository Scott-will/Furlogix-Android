package com.example.vetapp.validators.reports

import com.example.vetapp.reports.FieldType
import com.example.vetapp.reports.FieldTypeValidator
import io.mockk.*
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FieldTypeValidatorTests {

    @Test
    fun `should return true for TEXT type with any value`() {
        // Given
        val type = FieldType.TEXT
        val value = "Any text"

        // When
        val result = FieldTypeValidator.validateFieldTypeWithValue(type, value)

        // Then
        assertTrue(result)
    }

    @Test
    fun `should return true for BOOLEAN type with valid boolean value`() {
        // Given
        val type = FieldType.BOOLEAN

        // When
        val resultTrue = FieldTypeValidator.validateFieldTypeWithValue(type, "true")
        val resultFalse = FieldTypeValidator.validateFieldTypeWithValue(type, "false")

        // Then
        assertTrue(resultTrue)
        assertTrue(resultFalse)
    }

    @Test
    fun `should return false for BOOLEAN type with invalid boolean value`() {
        // Given
        val type = FieldType.BOOLEAN

        // When
        val result = FieldTypeValidator.validateFieldTypeWithValue(type, "invalidBoolean")

        // Then
        assertFalse(result)
    }

    @Test
    fun `should return true for NUMBER type with valid numeric value`() {
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
    fun `should return false for NUMBER type with invalid numeric value`() {
        // Given
        val type = FieldType.NUMBER

        // When
        val result = FieldTypeValidator.validateFieldTypeWithValue(type, "invalidNumber")

        // Then
        assertFalse(result)
    }
}
