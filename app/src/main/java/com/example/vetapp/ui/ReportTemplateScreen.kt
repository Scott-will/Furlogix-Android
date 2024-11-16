package com.example.vetapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.reports.ReportTemplateField
import com.example.vetapp.viewmodels.ReportViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asLiveData
import com.example.vetapp.ui.componets.reports.AddReportDialog

@Composable
fun ReportTemplateScreen(viewModel: ReportViewModel = hiltViewModel()
) {

    val reportState = viewModel.reportTemplateFields.asLiveData()
    val reports = viewModel.reports.asLiveData()
    //var formItems by remember { mutableStateOf(listOf<ReportTemplateField>()) }
    var showDialog by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Type 1") }

    // Button to show the dialog
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { showDialog = true }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Add New Item")
        }

        // Show the list of form items
        Spacer(modifier = Modifier.height(16.dp))
        reports.value?.forEach { item ->
            Text("${item.Name}")
        }
    }

    // Show the Material 3 Dialog for adding new item
    if (showDialog) {
        AddReportDialog(
            onDismiss = { showDialog = false },
            onSave = { newItem ->
                //reportState = reportState + newItem
                //showDialog = false
            },
            currentLabel = label,
            onLabelChange = { label = it },
            selectedType = selectedType,
            onTypeChange = { selectedType = it }
        )
    }
}
