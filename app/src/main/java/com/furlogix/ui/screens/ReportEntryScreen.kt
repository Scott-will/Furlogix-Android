package com.furlogix.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.ui.components.common.ErrorDialog
import com.furlogix.ui.components.reports.TooManyReportsWarning
import com.furlogix.ui.componets.reports.ReportEntryForm
import com.furlogix.viewmodels.ReportViewModel

@Composable
fun ReportEntryScreen(navController: NavController, reportId : Int = 0, viewModel: ReportViewModel = hiltViewModel()
) {
    val reportName = viewModel.getReportNameById(reportId).collectAsState("")//reportName.value.filter { it.Id == reportId }.first().name
    val templateValueMap = remember { mutableMapOf<Int, MutableState<String>>() }
    val templates = viewModel.reportTemplatesForCurrentReport.collectAsState().value
    val isError = viewModel.isError.collectAsState()
    val errorMsg = viewModel.errorMsg.collectAsState()
    val isTooManyReports = viewModel.isTooManyReports.collectAsState()

    LaunchedEffect(reportId) {
        if (reportId != 0) {
            viewModel.populateReportTemplatesForCurrentReport(reportId)
        }
    }

    templates.forEach { template ->
        templateValueMap[template.uid] = mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isTooManyReports.value){
            TooManyReportsWarning()
        }
        ReportEntryForm(
            reportName = reportName.value,
            fields = templates,
            templateValueMap = templateValueMap
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.insertReportEntry(templateValueMap, reportId)
        }) {
            Text("Save")
        }
        Button(onClick = { viewModel.gatherReportData(reportId) }) {
            Text("Send Reports")
        }
    }
    if(isError.value){
        ErrorDialog( {
            viewModel.UpdateErrorState(false, "")
        }, errorMsg.value)
    }

}


