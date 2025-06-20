package com.furlogix.ui.reports

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.reports.FieldType
import com.furlogix.ui.components.reports.write.AddReportTemplateDialog
import org.junit.Rule
import org.junit.Test

class AddReportTemplateFieldDialogTest {
    @Composable
    fun AddReportTemplateFieldDialogTestContent(
        onDismiss: () -> Unit = {},
        onSave : (ReportTemplateField) -> Unit = {},
        currentLabel : String,
        currentUnits : String,
        update : Boolean = false,
        report : ReportTemplateField,
        reportId : Int,
        selectedType : FieldType
    ){
        AddReportTemplateDialog(
            onDismiss = onDismiss,
            onSave = onSave,
            currentLabel = currentLabel,
            currentUnit = currentUnits,
            selectedType = selectedType.toString(),
            update = update,
            reportField = report,
            reportId = reportId)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    /*need test for :
    diplay right name
    display right type
    dropdown menu expand and show right values
    on save triggered
    on dismiss
    handles update vs create logic
    shows report id
    when text changes its diaplaying new text
    when type changes its diaplaying new type
    */

    @Test
    fun AddReportTemplateFieldDialog_DisplaysRightName(){
        var reportTemplate = ReportTemplateField(name = "Test", reportId = 1, icon="test", fieldType = FieldType.NUMBER, units = "")

        composeTestRule.setContent {
            AddReportTemplateFieldDialogTestContent(
                report = reportTemplate,
                reportId = reportTemplate.reportId,
                currentLabel = reportTemplate.name,
                currentUnits = "",
                selectedType = reportTemplate.fieldType)
        }

        composeTestRule.onNodeWithText("Test").assertIsDisplayed()
    }

    @Test
    fun AddReportTemplateFieldDialog_DisplaysRightEnum() {
        val reportTemplate = ReportTemplateField(name = "Test", reportId = 1, icon="test", fieldType = FieldType.CHECKBOX, units = "")

        composeTestRule.setContent {
            AddReportTemplateFieldDialogTestContent(
                report = reportTemplate,
                reportId = reportTemplate.reportId,
                currentLabel = reportTemplate.name,
                currentUnits = "",
                selectedType = reportTemplate.fieldType
            )
        }

        composeTestRule.onNodeWithText(reportTemplate.fieldType.toString()).assertIsDisplayed()

    }

    @Test
    fun AddReportTemplateFieldDialog_TypeDropDownShowsCorrect(){
        var reportTemplate = ReportTemplateField(name = "Test", reportId = 1, icon="test", fieldType = FieldType.NUMBER, units = "")

        composeTestRule.setContent {
            AddReportTemplateFieldDialogTestContent(
                report = reportTemplate,
                reportId = reportTemplate.reportId,
                currentLabel = reportTemplate.name,
                currentUnits = "",
                selectedType = reportTemplate.fieldType)
        }
        composeTestRule.onNodeWithText(reportTemplate.fieldType.toString()).performClick()
        composeTestRule.waitForIdle()
        FieldType.values().forEach { fieldType ->
            //skip number since two will show
            if(fieldType != FieldType.NUMBER){
                composeTestRule.onNodeWithText(fieldType.toString()).assertIsDisplayed()
            }

        }
    }

    @Test
    fun AddReportTemplateFieldDialog_OnsaveTriggered(){
        var reportTemplate = ReportTemplateField(name = "Test", reportId = 1, icon="test", fieldType = FieldType.NUMBER, units = "")
        var savedReportTemplate : ReportTemplateField? = null
        var onSave : (ReportTemplateField) -> Unit = {savedReportTemplate = it}
        composeTestRule.setContent {
            AddReportTemplateFieldDialogTestContent(
                report = reportTemplate,
                reportId = reportTemplate.reportId,
                currentLabel = reportTemplate.name,
                currentUnits = "",
                selectedType = reportTemplate.fieldType,
                onSave = onSave)
        }
        composeTestRule.onNodeWithText("Save").performClick()
        assert(savedReportTemplate != null)
        assert(reportTemplate.name == savedReportTemplate?.name)
    }

    @Test
    fun AddReportTemplateFieldDialog_UpdateTrue(){
        var reportTemplate = ReportTemplateField(name = "Test", reportId = 1, icon="test", fieldType = FieldType.NUMBER, units = "")
        val onUpdate : (ReportTemplateField) -> Unit = {reportTemplate = it}
        composeTestRule.setContent {
            AddReportTemplateFieldDialogTestContent(
                report = reportTemplate,
                reportId = reportTemplate.reportId,
                currentLabel = reportTemplate.name,
                currentUnits = "",
                selectedType = reportTemplate.fieldType,
                onSave = onUpdate)
        }

        composeTestRule.onNodeWithText("Test").performTextReplacement("Changed It")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Changed It").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").performClick()

        assert(reportTemplate.name == "Changed It")
    }

    @Test
    fun AddReportTemplateFieldDialog_UpdateFalse(){
        var reportTemplate = ReportTemplateField(name = "Test", reportId = 1, icon="test", fieldType = FieldType.NUMBER, units = "")
        var savedTemplate : ReportTemplateField? = null
        val onUpdate : (ReportTemplateField) -> Unit = {savedTemplate = it}
        composeTestRule.setContent {
            AddReportTemplateFieldDialogTestContent(
                report = reportTemplate,
                reportId = reportTemplate.reportId,
                currentLabel = reportTemplate.name,
                currentUnits = "",
                selectedType = reportTemplate.fieldType,
                onSave = onUpdate)
        }

        composeTestRule.onNodeWithText("Test").performTextReplacement("Changed It")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Changed It").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").performClick()

        assert(reportTemplate.name != savedTemplate?.name)
        assert(reportTemplate.name == "Test")
    }

    @Test
    fun AddReportTemplateFieldDialog_OnTextFieldChanges(){
        var reportTemplate = ReportTemplateField(name = "Test", reportId = 1, icon="test", fieldType = FieldType.NUMBER, units = "")

        composeTestRule.setContent {
            AddReportTemplateFieldDialogTestContent(
                report = reportTemplate,
                reportId = reportTemplate.reportId,
                currentLabel = reportTemplate.name,
                currentUnits = "",
                selectedType = reportTemplate.fieldType)
        }
        composeTestRule.onNodeWithText("Test").performTextReplacement("Changed It")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Changed It").assertIsDisplayed()
    }

    @Test
    fun AddReportTemplateFieldDialog_OnTypeFieldChanges(){
        var reportTemplate = ReportTemplateField(name = "Test", reportId = 1, icon="test", fieldType = FieldType.NUMBER, units = "")

        composeTestRule.setContent {
            AddReportTemplateFieldDialogTestContent(
                report = reportTemplate,
                reportId = reportTemplate.reportId,
                currentLabel = reportTemplate.name,
                currentUnits = "",
                selectedType = reportTemplate.fieldType)
        }

        FieldType.values().forEach {fieldType ->
            composeTestRule.onNodeWithText(reportTemplate.fieldType.toString()).performClick()
            composeTestRule.onNodeWithText(fieldType.toString()).performClick()
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithText(fieldType.toString()).assertIsDisplayed()
            reportTemplate.fieldType = fieldType
        }
    }
}