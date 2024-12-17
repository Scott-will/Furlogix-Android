package com.example.vetapp.ui.components.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.testTag
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.reports.FieldType

@Composable
fun ReportEntry(reportTemplateField: ReportTemplateField, text : MutableState<String>) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    )
    {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(reportTemplateField.name)
            Spacer(modifier = Modifier.height(16.dp))
            when (reportTemplateField.fieldType){
                FieldType.NUMBER -> {
                    // Input field for numbers
                    OutlinedTextField(
                        value = text.value,
                        onValueChange = { newValue ->
                            // Only update value if the input is a valid number
                            if (newValue.isEmpty() || newValue.toDoubleOrNull() != null) {
                                text.value = newValue
                            }
                        },
                        label = { Text("Enter Number") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
                FieldType.BOOLEAN -> {
                    // Checkbox for boolean input (true/false)
                    val isChecked = remember { mutableStateOf(text.value.toBoolean()) }
                    Checkbox(
                        checked = isChecked.value,
                        onCheckedChange = {
                            isChecked.value = it
                            text.value = it.toString()  // Store true/false as a string
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .testTag("CheckBox")
                    )
                    Text("Check if true")
                }
                FieldType.TEXT -> {
                    // Input field for text
                    TextField(
                        value = text.value,
                        onValueChange = { text.value = it },
                        label = { Text("Enter Text") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                    )
                }
                else -> Text("Error in Template!!")
            }
        }
    }
}