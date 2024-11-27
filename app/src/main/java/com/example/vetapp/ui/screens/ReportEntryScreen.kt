package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.componets.reports.ReportEntryForm
import com.example.vetapp.viewmodels.ReportViewModel

@Composable
fun ReportEntryScreen(navController: NavController, reportId : Int = 0, viewModel: ReportViewModel = hiltViewModel()
) {
    val reportName = viewModel.reports.collectAsState().value.filter { it.Id == reportId }.first().Name
    var templateValueMap = remember { mutableMapOf<Int, MutableState<String>>() }
    val templates = viewModel.reportTemplateFields.collectAsState().value.filter { it.reportId == reportId }
    templates.forEach { template ->
        templateValueMap[template.uid] = mutableStateOf("")
    }
    ReportEntryForm(reportName = reportName, fields = templates, templateValueMap = templateValueMap)
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = {viewModel.insertReportEntry(templateValueMap, reportId)}) {
        Text("Save")
    }
}