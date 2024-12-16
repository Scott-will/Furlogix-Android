package com.example.vetapp.ui.componets

import android.app.TimePickerDialog
import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun ReportsReminder() {
    var reminderText by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    var selectedTime by remember { mutableStateOf<Calendar?>(null) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Set a Reminder")

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = reminderText,
            onValueChange = { reminderText = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                if (reminderText.isEmpty()) {
                    Text("Enter reminder message...")
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            showDatePicker = true
        }) {
            Text("Select Date")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            showTimePicker = true
        }) {
            Text("Select Time")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (reminderText.isNotEmpty() && selectedDate != null && selectedTime != null) {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, selectedDate!!.get(Calendar.YEAR))
                calendar.set(Calendar.MONTH, selectedDate!!.get(Calendar.MONTH))
                calendar.set(Calendar.DAY_OF_MONTH, selectedDate!!.get(Calendar.DAY_OF_MONTH))
                calendar.set(Calendar.HOUR_OF_DAY, selectedTime!!.get(Calendar.HOUR_OF_DAY))
                calendar.set(Calendar.MINUTE, selectedTime!!.get(Calendar.MINUTE))
                //setReminder(context = LocalContext.current, reminderText = reminderText, calendar)
            }
        }) {
            Text("Set Reminder")
        }
    }
    if(showDatePicker){
        showDatePicker( { date ->
            selectedDate = date
            showDatePicker = false
        })
    }
    if(showTimePicker){
        showTimePicker({ time ->
            selectedTime = time
            showTimePicker = false
        })
    }
}

@Composable
fun showDatePicker(onDateSelected: (Calendar) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePicker.show()
}

@Composable
fun showTimePicker(onTimeSelected: (Calendar) -> Unit) {
    val calendar = Calendar.getInstance()
    val timePicker = TimePickerDialog(
        LocalContext.current,
        { _, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            onTimeSelected(selectedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    timePicker.show()
}