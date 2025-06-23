package com.furlogix.ui.reports

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.furlogix.Database.Entities.Reports
import com.furlogix.ui.components.reports.read.ReportItem
import org.junit.Rule
import org.junit.Test

class ReportItemTest {
    @Composable
    fun ReportItemTestContent(
        data: Reports,
        onClick: (Reports) -> Unit = {},
        onDeleteClick: (Reports) -> Unit = {},
        onUpdateClick: (Reports) -> Unit = {},
        onSendClick: (Reports) -> Unit = {}
    ) {
        ReportItem(
            data = data,
            onClick = onClick,
            onDeleteClick = onDeleteClick,
            onEditClick = onUpdateClick,
            onSendClick = onSendClick
        )
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun reportItem_displaysCorrectName() {
        // Given
        val report = Reports(name = "Test", petId = 1)

        composeTestRule.setContent {
            ReportItemTestContent(data = report)
        }

        // When
        composeTestRule.onNodeWithText("Test").assertIsDisplayed()
    }}