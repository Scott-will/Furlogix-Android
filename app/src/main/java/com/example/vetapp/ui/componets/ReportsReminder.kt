package com.example.vetapp.ui.componets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
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
    var expanded by remember { mutableStateOf(false) }

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
        Text(text = "Recurrence", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                recurrenceOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            recurrenceOption = option
                        },
                        text = {Text(text = option)} ,
                    )
                }
            }
        }

        Text(text = "Reminder Type", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                reminderTypes.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            reminderType = option
                        },
                        text = {Text(text = option)} ,
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
