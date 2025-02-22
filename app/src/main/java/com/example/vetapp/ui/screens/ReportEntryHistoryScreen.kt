package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.components.reports.ReportEntryHistoryTable
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.ReportViewModel

@Composable
fun ReportEntryHistoryScreen(navController: NavController, reportId : Int = 0, viewModel: ReportViewModel = hiltViewModel()
) {
    LaunchedEffect(reportId) {
        viewModel.populateReportTemplatesForCurrentReport(reportId)
    }

    val templates = viewModel.reportTemplatesForCurrentReport.collectAsState()
    Button(onClick = {navController.navigate(Screen.ReportEntry.route
        .replace("{reportId}", "${reportId}"))}){
        Text("Add Data")
    }
    Column {
        ReportEntryHistoryTable(templates.value)

    }
}