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
import com.example.vetapp.ui.components.reports.ReportItem
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

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