package com.example.vetapp.ui.screens

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
import com.example.vetapp.ui.componets.common.AddItemButton
import com.example.vetapp.ui.componets.reports.AddReportDialog
import com.example.vetapp.ui.componets.reports.ReportsList

@Composable
fun ReportScreen(navController: NavController, viewModel: ReportViewModel = hiltViewModel()
) {

    var reports = viewModel.reports.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf("") }

    // Button to show the dialog
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Show the list of form items
        Spacer(modifier = Modifier.height(16.dp))
        ReportsList(reports.value, navController,
            onDeleteClick = {item -> viewModel.deleteReport(item)},
            onUpdateClick = {item -> viewModel.updateReport(item)})
        Spacer(modifier = Modifier.height(16.dp))
        AddItemButton(onClick = {showDialog = true}, localModifier = Modifier
            .size(56.dp) // Size of the button
            .background(
                color = Color.Gray, // Background color of the button
                shape = CircleShape // Circular shape
            )
            .align(Alignment.Start))
    }

    // Show the Material 3 Dialog for adding new item
    if (showDialog) {
        AddReportDialog(
            onDismiss = { showDialog = false },
            onSave = { newItem ->
                viewModel.insertReport(newItem.Name)
                label = ""
                showDialog = false
            },
            currentLabel = label,
        )
    }
}
