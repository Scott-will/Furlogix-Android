package com.example.vetapp.ui.componets.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.reports.FieldType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReportTemplateDialog(
    onDismiss:() -> Unit,
    onSave: (ReportTemplateField) -> Unit,
    currentLabel: String,
    selectedType: String,
    reportId : Int,
    update : Boolean = false,
    reportField : ReportTemplateField? = null){
    val fieldTypes = FieldType.values()
    var textFieldValue by remember { mutableStateOf(currentLabel) }
    var typeFieldValue by remember { mutableStateOf(selectedType) }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth().testTag("AddReportTemplateDialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                var expanded by remember { mutableStateOf(false) }
                val icon = if (expanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown
                //field name
                Text("Field Name: ")
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    label = { Text("Enter Field Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                //drop down for field type selection
                Text("Input Type:")
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {
                    OutlinedTextField(
                        value = typeFieldValue,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Select Input Type") },
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        fieldTypes.forEach { type ->
                            DropdownMenuItem(
                                onClick = {
                                    typeFieldValue = type.toString()
                                    expanded = false // Close the dropdown when an item is selected
                                },
                                text = {Text( text = type.toString() )}
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Submit button to save the new field
                Button(
                    onClick = {
                        // Create the new FormField and save it using onSave
                        if(update){
                            reportField?.name = textFieldValue
                            reportField?.fieldType = enumValueOf<FieldType>(typeFieldValue)
                            onSave(reportField!!)
                        }
                        else{
                            val newField = ReportTemplateField(
                                reportId = reportId,
                                name = textFieldValue,
                                fieldType = FieldType.valueOf(typeFieldValue) // Convert string to FieldType
                            )
                            onSave(newField)
                        }

                        onDismiss() // Close the dialog after saving
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally).testTag("AddReportTemplateDialogSave")
                )
                {
                    Text("Save")
                }
            }
        }
    }
}
