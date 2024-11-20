package com.example.vetapp.ui.componets.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vetapp.reports.FieldType
import com.example.vetapp.reports.ReportTemplateField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReportTemplateDialog(
    onDismiss:() -> Unit,
    onSave: (ReportTemplateField) -> Unit,
    currentLabel: String,
    onLabelChange: (String) -> Unit,
    selectedType: String,
    onTypeChange: (String) -> Unit){
    val fieldTypes = FieldType.values()
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
                Text("Field Name: ")
                OutlinedTextField(
                    value = currentLabel,
                    onValueChange = onLabelChange,
                    label = { Text("Enter Field Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                //drop down for field type selection
                Text("Input Type:")
                var expanded by remember{ mutableStateOf<Boolean>(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {expanded = !expanded}
                ) {
                    OutlinedTextField(
                        value = selectedType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Input Type") },
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        fieldTypes.forEach { type ->
                            DropdownMenuItem(onClick = {
                                onTypeChange(type.toString())
                                expanded = false
                            }, text = { Text(type.toString()) })

                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Submit button to save the new field
                Button(
                    onClick = {
                        // Create the new FormField and save it using onSave
                        val newField = ReportTemplateField(
                            label = currentLabel,
                            fieldType = FieldType.valueOf(selectedType) // Convert string to FieldType
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
