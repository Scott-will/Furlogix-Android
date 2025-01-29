package com.example.vetapp.ui.components.reminders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vetapp.ui.componets.common.DatePickerDialog
import com.example.vetapp.ui.componets.common.TimePickerDialog
import com.example.vetapp.viewmodels.RemindersViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsReminder(remindersViewModel: RemindersViewModel) {
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var recurrenceOption by remember { mutableStateOf("Once") }
    var reminderType by remember { mutableStateOf("Send") }
    var isDatePickerOpen by remember { mutableStateOf(false) }
    var isTimePickerOpen by remember { mutableStateOf(false) }
    var reminderExpanded by remember { mutableStateOf(false) }
    var recurrenceExpanded by remember { mutableStateOf(false) }

    val recurrenceOptions = listOf("Once", "Daily", "Weekly", "Monthly")
    val reminderTypes = listOf("Send", "Fill")

    if (isDatePickerOpen) {
        DatePickerDialog(onDateSelected = { date ->
            selectedDate = date
            isDatePickerOpen = false
        })
    }

    // Time Picker Dialog
    if (isTimePickerOpen) {
        TimePickerDialog(onTimeSelected = { time ->
            selectedTime = time
            isTimePickerOpen = false
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Schedule a Reminder", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Date Picker Button
        OutlinedButton(onClick = { isDatePickerOpen = true }) {
            Text(text = if (selectedDate.isEmpty()) "Select Date" else "Date: $selectedDate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time Picker Button
        OutlinedButton(onClick = { isTimePickerOpen = true }) {
            Text(text = if (selectedTime.isEmpty()) "Select Time" else "Time: $selectedTime")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Recurrence Option Dropdown
        Text(text = "Frequency", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
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

        Text(text = "Reminder Type", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = reminderExpanded,
            onExpandedChange = { reminderExpanded = !reminderExpanded },
        ) {
            OutlinedTextField(
                value = reminderType,
                onValueChange = { },
                readOnly = true,
                label = { Text("Select Reminder Type") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = reminderExpanded,
                onDismissRequest = { reminderExpanded = false }
            ) {
                reminderTypes.forEach { type ->
                    DropdownMenuItem(
                        onClick = {
                            reminderType = type
                            reminderExpanded = false // Close the dropdown when an item is selected
                        },
                        text = {Text( text = type )}
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Schedule Button
        Button(onClick = {
            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                remindersViewModel.scheduleReminder(selectedDate, selectedTime, recurrenceOption, reminderType)
            }
        }) {
            Text(text = "Schedule Reminder")
        }
    }
}
