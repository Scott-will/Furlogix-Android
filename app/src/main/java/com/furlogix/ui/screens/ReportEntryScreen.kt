package com.furlogix.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.furlogix.ui.components.reports.read.TooManyReportsWarning
import com.furlogix.ui.components.reports.write.ReportEntryForm
import com.furlogix.viewmodels.ReportEntryViewModel
import com.furlogix.viewmodels.ReportTemplatesViewModels
import com.furlogix.viewmodels.ReportViewModel

@Composable
fun ReportEntryScreen(navController: NavController, reportId : Int = 0,
                      reportViewModel: ReportViewModel = hiltViewModel(),
                      reportTemplatesViewModel: ReportTemplatesViewModels = hiltViewModel(),
                      reportEntryViewModel: ReportEntryViewModel = hiltViewModel()
) {
    val reportName = reportViewModel.getReportNameById(reportId).collectAsState("")
    val templateValueMap = remember { mutableMapOf<Int, MutableState<String>>() }
    val timestamp = remember { mutableStateOf<String>("") }
    val templates = reportTemplatesViewModel.reportTemplatesForCurrentReport.collectAsState().value
    val isError = reportEntryViewModel.isError.collectAsState()
    val errorMsg = reportEntryViewModel.errorMsg.collectAsState()
    val isTooManyReports = reportViewModel.isTooManyReports.collectAsState()

    LaunchedEffect(reportId) {
        if (reportId != 0) {
            reportTemplatesViewModel.populateReportTemplatesForCurrentReport(reportId)
        }
    }

    templates.forEach { template ->
        templateValueMap[template.uid] = mutableStateOf("")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            if(isTooManyReports.value){
                TooManyReportsWarning()
            }
        }
        item{
            ReportEntryForm(
                reportName = reportName.value,
                fields = templates,
                templateValueMap = templateValueMap,
                timestamp = timestamp
            )
        }
        item{
            Spacer(modifier = Modifier.height(16.dp))
        }
        item{
            Button(onClick = {
                reportEntryViewModel.insertReportEntry(templateValueMap, reportId, timestamp.value)
                navController.popBackStack()
            }) {
                Text("Save")
            }
        }
    }
    if(isError.value){
        ErrorDialog( {
            reportViewModel.UpdateErrorState(false, "")
        }, errorMsg.value)
    }

}


