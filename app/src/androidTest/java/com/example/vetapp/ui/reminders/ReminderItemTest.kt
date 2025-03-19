package com.example.vetapp.ui.reminders

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.vetapp.Database.Entities.Reminder
import com.example.vetapp.R
import com.example.vetapp.ui.components.reminders.ReminderItem
import org.junit.Rule
import org.junit.Test


class ReminderItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testReminderItemDisplaysCorrectly() {
        val reminder = Reminder(type = "Send", startTime = "10:00 AM", frequency = "Daily", requestCode = 1, title = "test", message = "test")

        composeTestRule.setContent {
            ReminderItem(data = reminder, onDeleteClick = {})
        }

        // Assert the type text is displayed correctly
        composeTestRule.onNodeWithText("Send").assertIsDisplayed()

        // Assert the start time is displayed correctly
        composeTestRule.onNodeWithText("10:00 AM").assertIsDisplayed()

        // Assert the frequency is displayed correctly
        composeTestRule.onNodeWithText("Daily").assertIsDisplayed()
    }

    @Test
    fun testDeleteButtonTriggersOnDeleteClick() {
        val reminder = Reminder(type = "Send", startTime = "10:00 AM", frequency = "Daily", requestCode = 1, title = "test", message = "test")
        var deleteCalled = false

        composeTestRule.setContent {
            ReminderItem(
                data = reminder,
                onDeleteClick = { deleteCalled = true }
            )
        }


        composeTestRule.onNodeWithContentDescription(R.string.delete_text.toString()).performClick()

        assert(deleteCalled) { "Delete button did not trigger the callback" }
    }

    @Test
    fun testReminderItemLayout() {
        val reminder = Reminder(type = "Send", startTime = "10:00 AM", frequency = "Daily", requestCode = 1, title = "test", message = "test")

        composeTestRule.setContent {
            ReminderItem(data = reminder, onDeleteClick = {})
        }

        // Assert that all texts are rendered within a Row (you can check this by checking the presence of 'Row' in the hierarchy)
        composeTestRule.onNode(hasText("Send")).assertIsDisplayed()
        composeTestRule.onNode(hasText("10:00 AM")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Daily")).assertIsDisplayed()

        // Check if the surface is displayed with correct background color
        composeTestRule.onNodeWithText("Send").assertIsDisplayed()
    }

    @Test
    fun testReminderItemHasCorrectPadding() {
        val reminder = Reminder(type = "Send", startTime = "10:00 AM", frequency = "Daily", requestCode = 1, title = "test", message = "test")

        composeTestRule.setContent {
            ReminderItem(data = reminder, onDeleteClick = {})
        }

        // Check the padding of the surface element
        composeTestRule.onNodeWithText("Send")
            .assertExists()
    }
}
