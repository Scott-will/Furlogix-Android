package com.example.vetapp.ui.componets.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vetapp.Database.Entities.Reports

@Composable
fun AddReportDialog(
    onDismiss:() -> Unit,
    onSave: (Reports) -> Unit,
    currentLabel: String,
    onLabelChange: (String) -> Unit){
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                //field name
                Text("Report Name: ")
                OutlinedTextField(
                    value = currentLabel,
                    onValueChange = onLabelChange,
                    label = { Text("Enter Field Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
                // Submit button to save the new field
                Button(
                    onClick = {
                        // Create the new FormField and save it using onSave
                        val newField = Reports(
                            Name = currentLabel,
                        )
                        onSave(newField)
                        onDismiss() // Close the dialog after saving
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                {
                    Text("Save")
                }
            }
        }
    }
}
