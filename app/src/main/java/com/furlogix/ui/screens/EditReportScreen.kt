package com.furlogix.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.reports.FieldType
import com.furlogix.ui.components.common.NoDataAvailable
import com.furlogix.ui.components.reports.write.AddReportTemplateDialog
import com.furlogix.ui.components.reports.read.ReporttemplatesList
import com.furlogix.viewmodels.ReportTemplatesViewModels
import com.furlogix.viewmodels.ReportViewModel

@Composable
fun EditReportScreen(navController: NavController, reportId : Int,
                     reportViewModel: ReportViewModel = hiltViewModel(),
                     reportTemplatesViewModel: ReportTemplatesViewModels = hiltViewModel()) {
    var showDialog by remember{ mutableStateOf(false) }
    var label by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(FieldType.entries.first().toString()) }
    val newTemplateList = remember { (mutableListOf<ReportTemplateField>())}
    val existingTemplateList = reportTemplatesViewModel.reportTemplatesForCurrentReport.collectAsState()
    val reports by reportViewModel.reports.collectAsState()

    val report = remember(reports, reportId) {
        reports.firstOrNull { it.Id == reportId }
    }
    val reportNameCopy = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(report) {
        if (report != null && report.name.isNotEmpty() && reportNameCopy.value == null) {
            reportNameCopy.value = report.name
        }
        reportTemplatesViewModel.populateReportTemplatesForCurrentReport(reportId)
    }

    val reportsTemplates = existingTemplateList.value + newTemplateList
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)){
            Button(onClick = {showDialog = true},
                modifier = Modifier.weight(1f)
                    .padding(top = 8.dp) ) {
                Text("Add report template", fontSize = 16.sp)
            }

            Button(onClick = {
                newTemplateList.forEach(){
                        item ->
                    reportTemplatesViewModel.insertReportTemplateField(item)
                }
                existingTemplateList.value.forEach(){
                        item ->
                    reportTemplatesViewModel.updateReportTemplateField(item)
                }
                if(report != null){
                    report.name = reportNameCopy.value.toString()
                    reportViewModel.updateReport(report)
                }

                reportTemplatesViewModel.populateReportTemplatesForCurrentReport(reportId)
                navController.popBackStack()
            },
                modifier = Modifier.weight(1f)
                    .padding(top = 8.dp) ) {
                Text("Save", fontSize = 16.sp)
            }
        }
        if (report != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Report Name: ")
            if (report.name.isEmpty()) {
                Text("Loading report...")
            }
            else {
                OutlinedTextField(
                    value = reportNameCopy.value ?: "",
                    onValueChange = { reportNameCopy.value = it },
                    label = { Text("Enter Field Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .fillMaxSize()){
            if(reportsTemplates.size == 0 ){
                NoDataAvailable("Report Fields", Modifier.fillMaxSize())
            }
            else{
                ReporttemplatesList(reportsTemplates,
                    onDeleteClick = {item -> reportTemplatesViewModel.deleteReportTemplateField(item)},
                    onUpdateClick = {item -> reportTemplatesViewModel.updateReportTemplateField(item)},
                )
            }
        }


    }
    if(showDialog){
        AddReportTemplateDialog(
            onDismiss = { showDialog = false },
            onSave = { newItem ->
                newTemplateList.add(newItem)
                label = ""
                selectedType = FieldType.entries.first().toString()
            },
            currentLabel = label,
            currentUnit = "",
            selectedType = selectedType,
            reportId = reportId
        )
    }
}