package com.example.vetapp.ui.reports

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.R
import com.example.vetapp.reports.FieldType
import com.example.vetapp.ui.componets.reports.ReportTemplateItem
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

class ReportTemplateItemTest {
    @Composable
    fun ReportTemplateItemTestContent(
        data : ReportTemplateField,
        onDeleteClick: (ReportTemplateField) -> Unit = {},
        onUpdateClick: (ReportTemplateField) -> Unit = {},
        onFavouriteClick : (ReportTemplateField) -> Unit = {}

    ){
        val navController = TestNavHostController(LocalContext.current)
        ReportTemplateItem(
            data = data,
            onDeleteClick = onDeleteClick,
            onUpdateClick = onUpdateClick,
            navController = navController,
            onFavouriteClick = onFavouriteClick
        )
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun reportTemplateItem_displaysCorrectName(){
        val template = ReportTemplateField(reportId = 1, name = "Test", fieldType = FieldType.BOOLEAN)
        composeTestRule.setContent {
            ReportTemplateItemTestContent(data = template)
        }

        composeTestRule.onNodeWithText("Test").assertIsDisplayed()
    }

    @Test
    fun reportTemplateItem_showsDialogOnEditClick(){
        val template = ReportTemplateField(reportId = 1, name = "Test", fieldType = FieldType.BOOLEAN)
        composeTestRule.setContent {
            ReportTemplateItemTestContent(
                data = template,
                onUpdateClick = {},
                onDeleteClick = {}
                )
        }
        composeTestRule.onNodeWithContentDescription(R.string.edit_text.toString()).performClick()

        // Then
        composeTestRule.onNodeWithTag("AddReportTemplateDialog").assertIsDisplayed()
    }

    @Test
    fun reportTemplateItem_triggersOnDeleteClick(){
        val template = ReportTemplateField(reportId = 1, name = "Test", fieldType = FieldType.BOOLEAN)
        var deletedTemplate: ReportTemplateField? = null
        val onDeleteClick: (ReportTemplateField) -> Unit = { deletedTemplate = it }

        composeTestRule.setContent {
            ReportTemplateItemTestContent(
                data = template,
                onUpdateClick = {},
                onDeleteClick = onDeleteClick
            )
        }
        composeTestRule.onNodeWithContentDescription(R.string.delete_text.toString()).performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        // Then
        assert(deletedTemplate != null)
        assert(template.name == deletedTemplate?.name)
    }
    @Test
    fun reportTemplateItem_triggersOnSaveFromDialog() {
        // Given
        val template = ReportTemplateField(reportId = 1, name = "Test", fieldType = FieldType.BOOLEAN)
        var updatedTemplate: ReportTemplateField? = null
        val onUpdateClick: (ReportTemplateField) -> Unit = { updatedTemplate = it }

        composeTestRule.setContent {
            ReportTemplateItemTestContent(
                data = template,
                onDeleteClick = {},
                onUpdateClick = onUpdateClick
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription(R.string.edit_text.toString()).performClick()
        sleep(100)
        composeTestRule.onNodeWithTag("AddReportTemplateDialogSave").performClick() // Simulate save action

        // Then
        assert(updatedTemplate != null)
        assert(template.name == updatedTemplate?.name)
    }

    @Test
    fun reportTemplateItem_dialogClosesOnDismiss() {
        // Given
        val template = ReportTemplateField(reportId = 1, name = "Test", fieldType = FieldType.BOOLEAN)

        composeTestRule.setContent {
            ReportTemplateItemTestContent(data = template)
        }

        // When
        composeTestRule.onNodeWithContentDescription(R.string.edit_text.toString()).performClick()  // Open the dialog
        composeTestRule.onNodeWithTag("AddReportTemplateDialogSave").performClick() // Simulate dismiss action

        // Then
        composeTestRule.onNodeWithTag("AddReportTemplateDialog").assertDoesNotExist() // Check if dialog is dismissed
    }

    @Test
    fun reportTemplateItem_togglesDialogVisibility() {
        // Given
        val template = ReportTemplateField(reportId = 1, name = "Test", fieldType = FieldType.BOOLEAN)

        composeTestRule.setContent {
            ReportTemplateItemTestContent(
                data = template,
                onDeleteClick = {},
                onUpdateClick = {}
            )
        }

        // When (Opening the dialog)
        composeTestRule.onNodeWithContentDescription(R.string.edit_text.toString()).performClick()
        composeTestRule.onNodeWithTag("AddReportTemplateDialog").assertIsDisplayed()

        // When (Closing the dialog)
        composeTestRule.onNodeWithTag("AddReportTemplateDialogSave").performClick()
        composeTestRule.onNodeWithTag("AddReportTemplateDialog").assertDoesNotExist()
    }
}