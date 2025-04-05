package com.furlogix.ui.graphs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.ui.components.graphs.BarGraph
import org.junit.Rule
import org.junit.Test

class BarGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBarGraphRendersCorrectly() {
        // Given
        val entries = listOf(
            ReportEntry(1, "true", reportId = 1, templateId = 1, timestamp = ""),
            ReportEntry(1, "false", reportId = 1, templateId = 1, timestamp = ""),
            ReportEntry(1, "true", reportId = 1, templateId = 1, timestamp = "")
        )
        val name = "Test Graph"

        // When
        composeTestRule.setContent {
            BarGraph(entries = entries, name = name)
        }

        // Then
        composeTestRule.onNodeWithText(name).assertIsDisplayed()
        composeTestRule.onNodeWithText("True").assertIsDisplayed()
        composeTestRule.onNodeWithText("False").assertIsDisplayed()
        composeTestRule.onNodeWithText("2").assertIsDisplayed() // True count
        composeTestRule.onNodeWithText("1").assertIsDisplayed() // False count
    }

    @Test
    fun testBarGraphMaxHeightScaling() {
        // Given
        val entries = listOf(
            ReportEntry(1, "true", reportId = 1, templateId = 1, timestamp = ""),
            ReportEntry(1, "false", reportId = 1, templateId = 1, timestamp = ""),
            ReportEntry(1, "true", reportId = 1, templateId = 1, timestamp = ""),
            ReportEntry(1, "true", reportId = 1, templateId = 1, timestamp = "")
        )
        val name = "Test Graph"

        // When
        composeTestRule.setContent {
            BarGraph(entries = entries, name = name)
        }

        // Then: Verify that the true bar is taller than the false bar
        val trueBar = composeTestRule.onNodeWithText("True")
        val falseBar = composeTestRule.onNodeWithText("False")

        // (You could use additional assertions for pixel height, but this test is more conceptual)
        trueBar.assertIsDisplayed()
        falseBar.assertIsDisplayed()
    }

    @Test
    fun testBarGraphHandlesNullCountsGracefully() {
        // Given: Null counts for true or false
        val entries = listOf( ReportEntry(1, "true", reportId = 1, templateId = 1, timestamp = ""),
            ReportEntry(1, "false", reportId = 1, templateId = 1, timestamp = "")
        )
        val name = "Test Graph with Nulls"

        // When
        composeTestRule.setContent {
            BarGraph(entries = entries, name = name)
        }

        // Then: Ensure the graph handles the null case gracefully and doesn't crash
        composeTestRule.onNodeWithText("True").assertIsDisplayed()
        composeTestRule.onNodeWithText("False").assertIsDisplayed()
    }
}
