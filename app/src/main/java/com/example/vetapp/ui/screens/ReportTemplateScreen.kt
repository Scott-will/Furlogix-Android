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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.reports.FieldType
import com.example.vetapp.ui.components.common.AddItemButton
import com.example.vetapp.ui.components.common.ErrorDialog
import com.example.vetapp.ui.components.common.NoDataAvailable
import com.example.vetapp.ui.components.common.TitleText
import com.example.vetapp.ui.components.reports.AddReportTemplateDialog
import com.example.vetapp.ui.components.reports.ReporttemplatesList
import com.example.vetapp.viewmodels.ReportViewModel

@Composable
fun ReportTemplateScreen(navController: NavController, reportId : Int = 0, reportName : String = "Report Template", viewModel: ReportViewModel = hiltViewModel()
) {

    val reportTemplateState = viewModel.reportTemplatesForCurrentReport.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val label by remember { mutableStateOf("") }
    val selectedType by remember { mutableStateOf(FieldType.entries.first().toString()) }
    val isError = viewModel.isError.collectAsState()
    val errorMsg = viewModel.errorMsg.collectAsState()

    LaunchedEffect(reportId) {
        if (reportId != 0) {
            viewModel.populateReportTemplatesForCurrentReport(reportId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TitleText(reportName.uppercase())
        Spacer(modifier = Modifier.height(16.dp))
        // Show the Material 3 Dialog for adding new item
        val reportsTemplates = reportTemplateState.value.filter { it.reportId == reportId }
        if(reportsTemplates.size == 0 ){
            NoDataAvailable("Report Fields", Modifier.fillMaxSize())
        }
        else{
            ReporttemplatesList(reportsTemplates,
                onDeleteClick = {item -> viewModel.deleteReportTemplateField(item)},
                onUpdateClick = {item -> viewModel.updateReportTemplateField(item)},
                onFavouriteClick = {item -> viewModel.updateFavouriteReportTemplateItem(item.uid)},
                navController)
        }
        Spacer(modifier = Modifier.height(16.dp))
        AddItemButton(onClick = { showDialog = true }, localModifier = Modifier
            .size(56.dp)
            .background(
                color = Color.Gray,
                shape = CircleShape
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
    if(isError.value){
        ErrorDialog( {
            viewModel.UpdateErrorState(false, "")
        }, errorMsg.value)
    }
}
