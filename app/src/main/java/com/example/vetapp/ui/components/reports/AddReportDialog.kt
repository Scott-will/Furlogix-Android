package com.example.vetapp.ui.components.reports

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vetapp.Database.Entities.Reports

@Composable
fun AddReportDialog(
    onDismiss:() -> Unit,
    onSave: (Reports) -> Unit,
    currentLabel: String,
    update : Boolean = false,
    report : Reports? = null){
    var textFieldValue by remember { mutableStateOf(currentLabel) }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
                .testTag("AddReportDialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                //field name
                Text("Report Name: ")
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    label = { Text("Enter Field Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                // Submit button to save the new field
                Button(
                    onClick = {
                        // Create the new FormField and save it using onSave
                        if(update){
                            report?.name = textFieldValue
                            onSave(report!!)
                        }
                        else{
                            val newField = Reports(
                                name = textFieldValue,
                                //TODO: CHANGE TO USE ACTUAL PET ID
                                petId = 1
                            )
                            onSave(newField)
                        }


                        onDismiss() // Close the dialog after saving
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally).testTag("AddReportDialogSave")
                )
                {
                    Text("Save")
                }
            }
        }
    }
}
