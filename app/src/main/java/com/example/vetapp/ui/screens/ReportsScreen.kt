package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.components.common.ErrorDialog
import com.example.vetapp.ui.components.reports.ReportsList
import com.example.vetapp.ui.components.reports.TooManyReportsWarning
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.ReportViewModel
import com.example.vetapp.viewmodels.UserViewModel

@Composable
fun ReportsScreen(navController: NavController, viewModel: ReportViewModel = hiltViewModel(), userViewModel: UserViewModel = hiltViewModel()) {

    val reports = viewModel.reports.collectAsState()
    val isError = viewModel.isError.collectAsState()
    val errorMsg = viewModel.errorMsg.collectAsState()
    val isTooManyReports = viewModel.isTooManyReports.collectAsState()
    val userId = userViewModel.userId.collectAsState(1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isTooManyReports.value){
            TooManyReportsWarning()
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.DeleteSentReports() }) {
                Text("Delete Sent Reports")
            }
        }
        // Show the list of form items
        Spacer(modifier = Modifier.height(16.dp))
        ReportsList(reports.value,
            onDeleteClick = {item -> viewModel.deleteReport(item)},
            onUpdateClick = {item -> viewModel.updateReport(item)},
            onClick = {data -> navController.navigate(Screen.ReportEntry.route.replace("{reportId}", "${data.Id}"))},
            editable = false,
            userId = userId.value)
        Spacer(modifier = Modifier.height(16.dp))
    }
    if(isError.value){
        ErrorDialog( {
            viewModel.UpdateErrorState(false, "")
        }, errorMsg.value)
    }

}