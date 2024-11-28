package com.example.vetapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vetapp.viewmodels.ReportViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.reports.FieldType
import com.example.vetapp.ui.componets.common.AddItemButton
import com.example.vetapp.ui.componets.common.NoDataAvailable
import com.example.vetapp.ui.componets.reports.AddReportTemplateDialog
import com.example.vetapp.ui.componets.reports.ReporttemplatesList
import androidx.lifecycle.asLiveData
import com.example.vetapp.ui.componets.reports.AddReportDialog

@Composable
fun ReportTemplateScreen(navController: NavController, reportId : Int = 0, viewModel: ReportViewModel = hiltViewModel()
) {

    val reportTemplateState = viewModel.reportTemplateFields.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(FieldType.values().first().toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Show the Material 3 Dialog for adding new item
        val reportsTemplates = reportTemplateState.value.filter { it.reportId == reportId }
        if(reportsTemplates.size == 0 ){
            NoDataAvailable("Report Fields")
        }
        else{
            ReporttemplatesList(reportsTemplates,
                onDeleteClick = {item -> viewModel.deleteReportTemplateField(item)},
                onUpdateClick = {item -> viewModel.updateReportTemplateField(item)})
        }
        Spacer(modifier = Modifier.height(16.dp))
        AddItemButton(onClick = { showDialog = true }, localModifier = Modifier
            .size(56.dp) // Size of the button
            .background(
                color = Color.Gray, // Background color of the button
                shape = CircleShape // Circular shape
            )
            .align(Alignment.Start))
        if (showDialog) {
            AddReportTemplateDialog(
                onDismiss = { showDialog = false },
                onSave = { newItem ->
                    viewModel.insertReportTemplateField(newItem)
                },
                currentLabel = label,
                selectedType = selectedType,
                reportId = reportId
            )
        }
    }
}
