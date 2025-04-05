package com.furlogix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.ui.components.common.AddItemButton
import com.furlogix.ui.components.common.ErrorDialog
import com.furlogix.ui.components.common.TitleText
import com.furlogix.ui.components.reports.AddReportDialog
import com.furlogix.ui.components.reports.ReportsList
import com.furlogix.ui.components.reports.TooManyReportsWarning
import com.furlogix.ui.navigation.Screen
import com.furlogix.viewmodels.ReportViewModel

@Composable
fun ManageReportScreen(navController: NavController, petId : Int, viewModel: ReportViewModel = hiltViewModel()) {

    var reports = viewModel.reports.collectAsState().value.filter { it.petId == petId }
    var showDialog by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf("") }
    val isError = viewModel.isError.collectAsState()
    val errorMsg = viewModel.errorMsg.collectAsState()
    val isTooManyReports = viewModel.isTooManyReports.collectAsState()

    // Button to show the dialog
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if(isTooManyReports.value){
            TooManyReportsWarning()
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.DeleteSentReports() }) {
                Text("Delete Sent Reports")
            }
        }
        // Show the list of form items
        TitleText("My Reports")
        ReportsList(reports,
            onDeleteClick = {item -> viewModel.deleteReport(item)},
            onEditClick = {item -> navController.navigate(Screen.EditReport.route.replace("{reportId}", item.Id.toString()))},
            onSendClick = {item -> viewModel.gatherReportData(item.Id)},
            onClick = {data -> navController.navigate(Screen.ReportEntryHistory.route.replace("{reportId}", "${data.Id}"))})
        Spacer(modifier = Modifier.height(16.dp))
        AddItemButton(onClick = {showDialog = true}, localModifier = Modifier
            .size(56.dp)
            .background(
                color = Color.Gray,
                shape = CircleShape
            )
            .align(Alignment.Start))
    }

    if (showDialog) {
        AddReportDialog(
            onDismiss = { showDialog = false },
            onSave = { newItem ->
                //navigate
                viewModel.insertReport(newItem.name, petId = petId)
                label = ""
                showDialog = false
            },
            currentLabel = label,
        )
    }
    if(isError.value){
        ErrorDialog( {
            viewModel.UpdateErrorState(false, "")
        }, errorMsg.value)
    }
}
