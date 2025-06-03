package com.furlogix.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.reports.FieldType
import com.furlogix.ui.components.common.NoDataAvailable
import com.furlogix.ui.components.reports.AddReportTemplateDialog
import com.furlogix.ui.components.reports.ReporttemplatesList
import com.furlogix.viewmodels.ReportViewModel

@Composable
fun EditReportScreen(reportId : Int, viewModel: ReportViewModel = hiltViewModel()) {
    var showDialog by remember{ mutableStateOf(false) }
    var label by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(FieldType.entries.first().toString()) }
    var newTemplateList = remember { (mutableListOf<ReportTemplateField>())}
    var existingTemplateList = viewModel.reportTemplatesForCurrentReport.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.populateReportTemplatesForCurrentReport(reportId)
    }
    val reportsTemplates = existingTemplateList.value + newTemplateList
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)){
            Button(onClick = {showDialog = true},
                modifier = Modifier.weight(1f)
                    .padding(top = 8.dp) ) {
                Text("Add report template")
            }

            Button(onClick = {
                newTemplateList.forEach(){
                        item ->
                    viewModel.insertReportTemplateField(item)
                }
                existingTemplateList.value.forEach(){
                        item ->
                    viewModel.updateReportTemplateField(item)
                    //update
                }
                viewModel.populateReportTemplatesForCurrentReport(reportId)},
                modifier = Modifier.weight(1f)
                    .padding(top = 8.dp) ) {
                Text("Save")
            }
        }
        Box(modifier = Modifier
            .fillMaxSize()){
            if(reportsTemplates.size == 0 ){
                NoDataAvailable("Report Fields", Modifier.fillMaxSize())
            }
            else{
                ReporttemplatesList(reportsTemplates,
                    onDeleteClick = {item -> viewModel.deleteReportTemplateField(item)},
                    onUpdateClick = {item -> viewModel.updateReportTemplateField(item)},
                    onFavouriteClick = {})
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
            selectedType = selectedType,
            reportId = reportId
        )
    }
}