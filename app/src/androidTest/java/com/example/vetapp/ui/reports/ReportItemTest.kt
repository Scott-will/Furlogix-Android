package com.example.vetapp.ui.reports

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.R
import com.example.vetapp.ui.componets.reports.ReportItem
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

class ReportItemTest {
    @Composable
    fun ReportItemTestContent(
        data: Reports,
        onClick: (Reports) -> Unit = {},
        onDeleteClick: (Reports) -> Unit = {},
        onUpdateClick: (Reports) -> Unit = {}
    ) {
        ReportItem(
            data = data,
            onClick = onClick,
            onDeleteClick = onDeleteClick,
            onUpdateClick = onUpdateClick
        )
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun reportItem_displaysCorrectName() {
        // Given
        val report = Reports(name = "Test")

        composeTestRule.setContent {
            ReportItemTestContent(data = report)
        }

        // When
        composeTestRule.onNodeWithText("Test").assertIsDisplayed()
    }

    @Test
    fun reportItem_triggersOnClick() {
        // Given
        val report = Reports(name = "Sample Report")
        var clickedReport: Reports? = null
        val onClick: (Reports) -> Unit = { clickedReport = it }

        composeTestRule.setContent {
            ReportItemTestContent(data = report, onClick = onClick)
        }

        // When
        composeTestRule.onNodeWithText("Sample Report").performClick()

        // Then
        assert(clickedReport != null)
        assert(report.name == clickedReport?.name)
    }

    @Test
    fun reportItem_showsDialogOnEditClick() {
        // Given
        val report = Reports(name = "Sample Report")

        composeTestRule.setContent {
            ReportItemTestContent(
                data = report,
                onClick = {},
                onDeleteClick = {},
                onUpdateClick = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription(R.string.edit_text.toString()).performClick()

        // Then
        composeTestRule.onNodeWithTag("AddReportDialog").assertIsDisplayed()
    }

    @Test
    fun reportItem_triggersOnDeleteClick() {
        // Given
        val report = Reports(name = "Sample Report")
        var deletedReport: Reports? = null
        val onDeleteClick: (Reports) -> Unit = { deletedReport = it }

        composeTestRule.setContent {
            ReportItemTestContent(data = report, onDeleteClick = onDeleteClick)
        }

        // When
        composeTestRule.onNodeWithContentDescription(R.string.delete_text.toString()).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("OK").performClick()

        // Then
        assert(deletedReport != null)
        assert(report.name == deletedReport?.name)
    }

    @Test
    fun reportItem_triggersOnSaveFromDialog() {
        // Given
        val report = Reports(name = "Sample Report")
        var updatedReport: Reports? = null
        val onUpdateClick: (Reports) -> Unit = { updatedReport = it }

        composeTestRule.setContent {
            ReportItemTestContent(
                data = report,
                onClick = {},
                onDeleteClick = {},
                onUpdateClick = onUpdateClick
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription(R.string.edit_text.toString()).performClick()
        sleep(100)
        composeTestRule.onNodeWithTag("AddReportDialogSave").performClick() // Simulate save action

        // Then
        assert(updatedReport != null)
        assert(report.name == updatedReport?.name)
    }

    @Test
    fun reportItem_dialogClosesOnDismiss() {
        // Given
        val report = Reports(name = "Sample Report")

        composeTestRule.setContent {
            ReportItemTestContent(data = report)
        }

        // When
        composeTestRule.onNodeWithContentDescription(R.string.edit_text.toString()).performClick()  // Open the dialog
        composeTestRule.onNodeWithTag("AddReportDialogSave").performClick() // Simulate dismiss action

        // Then
        composeTestRule.onNodeWithTag("AddReportDialog").assertDoesNotExist() // Check if dialog is dismissed
    }

    @Test
    fun reportItem_togglesDialogVisibility() {
        // Given
        val report = Reports(name = "Sample Report")

        composeTestRule.setContent {
            ReportItemTestContent(
                data = report,
                onClick = {},
                onDeleteClick = {},
                onUpdateClick = {}
            )
        }

        // When (Opening the dialog)
        composeTestRule.onNodeWithContentDescription(R.string.edit_text.toString()).performClick()
        composeTestRule.onNodeWithTag("AddReportDialog").assertIsDisplayed()

        // When (Closing the dialog)
        composeTestRule.onNodeWithTag("AddReportDialogSave").performClick()
        composeTestRule.onNodeWithTag("AddReportDialog").assertDoesNotExist()
    }
}