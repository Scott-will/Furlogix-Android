package com.example.vetapp.ui.reports

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.ui.components.reports.AddReportDialog
import org.junit.Rule
import org.junit.Test

class AddReportDialogTest {
    @Composable
    fun AddReportDialogTestContent(
        onDismiss: () -> Unit = {},
        onSave : (Reports) -> Unit = {},
        currentLabel : String,
        update : Boolean = false,
        report : Reports
    ){
        AddReportDialog(
            onDismiss = onDismiss,
            onSave = onSave,
            currentLabel = currentLabel,
            update = update,
            report = report)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun AddReportDialog_DisplaysRightName(){
        val report = Reports(name = "Test", userId = 1)

        composeTestRule.setContent {
            AddReportDialogTestContent(report = report, currentLabel = report.name)
        }

        composeTestRule.onNodeWithText("Test").assertIsDisplayed()
    }

    @Test
    fun AddReportDialog_OnSaveTriggered(){
        val report = Reports(name = "Test", userId = 1)
        var savedReport : Reports? = null
        val onUpdate: (Reports) -> Unit = {savedReport = it}

        composeTestRule.setContent {
            AddReportDialogTestContent(report = report, currentLabel = report.name, onSave = onUpdate)
        }
        composeTestRule.onNodeWithText("Save").performClick()
        assert(savedReport != null)
        assert(report.name == savedReport?.name)
    }

    @Test
    fun AddReportDialog_OnUpdate_True(){
        var report = Reports(name = "Test", userId = 1)
        val onUpdate: (Reports) -> Unit = {report = it}

        composeTestRule.setContent {
            AddReportDialogTestContent(report = report, currentLabel = report.name, onSave = onUpdate, update = true)
        }
        //type into field
        composeTestRule.onNodeWithText("Test").performTextReplacement("Changed It")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Changed It").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").performClick()

        assert(report.name == "Changed It")
    }

    @Test
    fun AddReportDialog_OnUpdate_False(){
        val report = Reports(name = "Test", userId = 1)
        var savedReport : Reports? = null
        val onUpdate: (Reports) -> Unit = {savedReport = it}

        composeTestRule.setContent {
            AddReportDialogTestContent(report = report, currentLabel = report.name, onSave = onUpdate, update = false)
        }
        //type into field
        composeTestRule.onNodeWithText("Test").performTextReplacement("Changed It")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Changed It").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").performClick()

        assert(report.name != savedReport?.name)
        assert(report.name == "Test")
    }

    @Test
    fun AddReportDialog_OnTextFieldChanges(){
        val report = Reports(name = "Test", userId = 1)

        composeTestRule.setContent {
            AddReportDialogTestContent(report = report, currentLabel = report.name)
        }
        //type into field
        composeTestRule.onNodeWithText("Test").performTextReplacement("Changed It")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Changed It").assertIsDisplayed()
    }

}