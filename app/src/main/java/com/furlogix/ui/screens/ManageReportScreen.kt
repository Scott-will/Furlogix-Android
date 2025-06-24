package com.furlogix.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.ui.components.common.AddItemButton
import com.furlogix.ui.components.common.ErrorDialog
import com.furlogix.ui.components.common.TitleText
import com.furlogix.ui.components.reports.write.AddReportDialog
import com.furlogix.ui.components.reports.read.ReportsList
import com.furlogix.ui.components.reports.read.TooManyReportsWarning
import com.furlogix.ui.navigation.Screen
import com.furlogix.viewmodels.PetViewModel
import com.furlogix.viewmodels.ReportViewModel

@Composable
fun ManageReportScreen(navController: NavController, petId : Int,
                       petViewModel: PetViewModel = hiltViewModel(),
                       reportViewModel: ReportViewModel = hiltViewModel()) {

    val reports = reportViewModel.reports.collectAsState().value.filter { it.petId == petId }
    var showDialog by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf("") }
    val isError = reportViewModel.isError.collectAsState()
    val errorMsg = reportViewModel.errorMsg.collectAsState()
    val isTooManyReports = reportViewModel.isTooManyReports.collectAsState()
    val currentPet = petViewModel.currentPet.collectAsState()

    LaunchedEffect(Unit) {
        petViewModel.populateCurrentPet(petId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isTooManyReports.value){
            TooManyReportsWarning()
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { reportViewModel.DeleteSentReports() }) {
                Text("Delete Sent Reports")
            }
        }
        // Show the list of form items
        Spacer(modifier = Modifier.height(16.dp))
        TitleText("${currentPet.value?.name} Reports", modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))
        ReportsList(reports,
            onDeleteClick = {item -> reportViewModel.deleteReport(item)},
            onEditClick = {item -> navController.navigate(Screen.EditReport.route.replace("{reportId}", item.Id.toString()))},
            onSendClick = {item -> reportViewModel.gatherReportData(item.Id)},
            onClick = {data -> navController.navigate(Screen.ReportEntryHistory.route.replace("{reportId}", "${data.Id}"))})
        Spacer(modifier = Modifier.height(16.dp))
    }
    Box(modifier = Modifier.fillMaxSize()) {
        // Your content goes here

        AddItemButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }

    if (showDialog) {
        AddReportDialog(
            onDismiss = { showDialog = false },
            onSave = { newItem ->
                //navigate
                reportViewModel.insertReport(newItem.name, petId = petId)
                label = ""
                showDialog = false
            },
            currentLabel = label,
        )
    }
    if(isError.value){
        ErrorDialog( {
            reportViewModel.UpdateErrorState(false, "")
        }, errorMsg.value)
    }
}
