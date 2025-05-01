package com.furlogix.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime

@Composable
fun SimpleDateTimePicker(
    onDateTimeSelected: (LocalDateTime?) -> Unit
) {
    var selectedDay by remember { mutableStateOf<String?>(null) }
    var selectedMonth by remember { mutableStateOf<String?>(null) }
    var selectedYear by remember { mutableStateOf<String?>(null) }
    var selectedHour by remember { mutableStateOf<String?>(null) }
    var selectedMinute by remember { mutableStateOf<String?>(null) }
    var useNow by remember { mutableStateOf(true) }

    // When "Now" is checked, update all selections and disable manual entry
    LaunchedEffect(useNow) {
        if (useNow) {
            val now = LocalDateTime.now()
            selectedDay = now.dayOfMonth.toString()
            selectedMonth = now.monthValue.toString()
            selectedYear = now.year.toString()
            selectedHour = now.hour.toString().padStart(2, '0')
            selectedMinute = now.minute.toString().padStart(2, '0')
            onDateTimeSelected(now)
        }
    }

    // Update callback when any field changes (only if not using "Now")
    LaunchedEffect(selectedDay, selectedMonth, selectedYear, selectedHour, selectedMinute, useNow) {
        if (!useNow &&
            listOf(selectedDay, selectedMonth, selectedYear, selectedHour, selectedMinute).all { it != null }
        ) {
            try {
                val dateTime = LocalDateTime.of(
                    selectedYear!!.toInt(),
                    selectedMonth!!.toInt(),
                    selectedDay!!.toInt(),
                    selectedHour!!.toInt(),
                    selectedMinute!!.toInt()
                )
                onDateTimeSelected(dateTime)
            } catch (e: Exception) {
                onDateTimeSelected(null)
            }
        } else if (!useNow) {
            onDateTimeSelected(null)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = useNow, onCheckedChange = { useNow = it })
            Text("Use current date & time")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Select Date")
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            selectedDay?.let {
                TextField(
                    value = it,
                    onValueChange = { selectedDay = it },
                    enabled = !useNow,
                    label = { Text("Day") },
                )
            }

            selectedMonth?.let {
                TextField(
                    value = it,
                    onValueChange = { selectedMonth = it },
                    enabled = !useNow,
                    label = { Text("Month") },
                )
            }

            selectedYear?.let {
                TextField(
                    value = it,
                    onValueChange = { selectedYear = it },
                    enabled = !useNow,
                    label = { Text("Year") },
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Time")
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            selectedHour?.let {
                TextField(
                    value = it,
                    onValueChange = { selectedHour = it },
                    enabled = !useNow,
                    label = { Text("Hour") },
                )
            }

            selectedMinute?.let {
                TextField(
                    value = it,
                    onValueChange = { selectedMinute = it },
                    enabled = !useNow,
                    label = { Text("Minute") },
                )
            }
        }
    }
}




