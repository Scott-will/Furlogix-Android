package com.furlogix.ui.reports

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.reports.FieldType
import com.furlogix.ui.components.reports.ReportEntry
import org.junit.Rule
import org.junit.Test

class ReportEntryTest {

    @Composable
    fun  ReportEntryTestContent(reportTemplateField: ReportTemplateField, text : MutableState<String>){
        ReportEntry(reportTemplateField, text)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ReportEntry_DisplaysRightName(){
        val reportTemplateField = ReportTemplateField(
            name = "Template Field Test",
            fieldType = FieldType.TEXT,
            reportId = 0,
        )

        val text = mutableStateOf("")

        composeTestRule.setContent {
            ReportEntryTestContent(reportTemplateField, text)
        }

        composeTestRule.onNodeWithText("Template Field Test").assertIsDisplayed()
    }

    @Test
    fun ReportEntry_DisplaysRightTextField_AndKeyboard_Number(){
        val reportTemplateField = ReportTemplateField(
            name = "Template Field Test",
            fieldType = FieldType.NUMBER,
            reportId = 0,
        )

        val text = mutableStateOf("")

        composeTestRule.setContent {
            ReportEntryTestContent(reportTemplateField, text)
        }
        composeTestRule.onNodeWithText("Enter Number").assertExists()

        // Act: Type into the TextField
        composeTestRule.onNodeWithText("Enter Number").performTextReplacement("123")

        // Assert: Ensure the text has been entered into the field
        composeTestRule.onNodeWithText("123").assertExists()

        assert(text.value == "123")
    }

    @Test
    fun ReportEntry_DisplaysRightTextField_AndKeyboard_Text(){
        val reportTemplateField = ReportTemplateField(
            name = "Template Field Test",
            fieldType = FieldType.TEXT,
            reportId = 0,
        )

        val text = mutableStateOf("")

        composeTestRule.setContent {
            ReportEntryTestContent(reportTemplateField, text)
        }

        composeTestRule.onNodeWithText("Enter Text").assertExists()

        // Act: Type into the TextField
        composeTestRule.onNodeWithText("Enter Text").performTextReplacement("Sample Text")

        // Assert: Ensure the text has been entered into the field
        composeTestRule.onNodeWithText("Sample Text").assertExists()
        assert(text.value == "Sample Text")
    }

    @Test
    fun ReportEntry_DisplaysCheckBox(){
        val reportTemplateField = ReportTemplateField(
            name = "Template Field Test",
            fieldType = FieldType.CHECKBOX,
            reportId = 0,
        )

        val text = mutableStateOf(false.toString())

        composeTestRule.setContent {
            ReportEntryTestContent(reportTemplateField, text)
        }

        composeTestRule.onNodeWithTag("CheckBox").assertExists()

        // Act: Click the checkbox
        composeTestRule.onNodeWithTag("CheckBox").performClick()

        assert(text.value == true.toString())
    }
}