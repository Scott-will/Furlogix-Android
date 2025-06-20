package com.furlogix.ui.components.reminders

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.furlogix.ui.components.common.SimpleDateTimePicker
import com.furlogix.viewmodels.RemindersViewModel
import java.time.LocalDateTime


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsReminder(onDismiss : () -> Unit, remindersViewModel: RemindersViewModel) {
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var recurrenceOption by remember { mutableStateOf("Once") }
    var reminderTitle by remember { mutableStateOf("") }
    var reminderText by remember { mutableStateOf("") }
    var recurrenceExpanded by remember { mutableStateOf(false) }

    val recurrenceOptions = listOf("Once", "Daily", "Weekly", "Monthly")

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
                .testTag("AddReportDialog")) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Schedule a Reminder", style = MaterialTheme.typography.titleLarge)

                TextField(value = reminderTitle,
                    onValueChange = {reminderTitle = it},
                    label = { Text("Title") })
                TextField(value = reminderText,
                    onValueChange = {reminderText = it},
                    label = { Text("Message") })
                // Date Picker Button

                // Recurrence Option Dropdown
                Text(text = "Frequency", style = MaterialTheme.typography.bodyMedium)
                ExposedDropdownMenuBox(
                    expanded = recurrenceExpanded,
                    onExpandedChange = { recurrenceExpanded = !recurrenceExpanded },
                ) {
                    OutlinedTextField(
                        value = recurrenceOption,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Select Frequency") },
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = recurrenceExpanded,
                        onDismissRequest = { recurrenceExpanded = false }
                    ) {
                        recurrenceOptions.forEach { type ->
                            DropdownMenuItem(
                                onClick = {
                                    recurrenceOption = type
                                    recurrenceExpanded = false // Close the dropdown when an item is selected
                                },
                                text = {Text( text = type )}
                            )
                        }
                    }
                }

                SimpleDateTimePicker() { item -> selectedDateTime = item }

                // Schedule Button
                Button(onClick = {
                    remindersViewModel.scheduleReminder(selectedDateTime, recurrenceOption, reminderTitle, reminderText)
                    onDismiss()
                }) {
                    Text(text = "Schedule Reminder")
                }
            } }
        }
    }
