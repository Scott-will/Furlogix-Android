package com.example.vetapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.vetapp.ui.components.common.NoDataAvailable
import com.example.vetapp.ui.components.reports.PendingReportsDialog
import com.example.vetapp.ui.componets.graphs.GraphsWidget
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.ReportViewModel
import com.example.vetapp.viewmodels.UserViewModel


@Composable
fun PetDashboardScreen(navController: NavController, petId : Int, userViewModel: UserViewModel = hiltViewModel(), petViewModel : PetViewModel = hiltViewModel(), reportViewModel: ReportViewModel = hiltViewModel()) {
    reportViewModel.PopulateFavouriteReportTemplates()
    var favouriteReports = reportViewModel.favouriteReportTemplates.collectAsState()
    userViewModel.populateCurrentUser()
    val photoUri by petViewModel.photoUri.collectAsState()
    val currentUser = userViewModel.currentUser.collectAsState()
    if(currentUser.value != null && currentUser.value?.pendingSentReports!!){
        PendingReportsDialog(onConfirm = {userViewModel.setNoPendingReportsForUser() }, onDismiss = {navController.navigate(Screen.Reports.route)})
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text("Welcome to the Dashboard!")
        if(favouriteReports.value.isEmpty()){
            NoDataAvailable("Favourite Report Templates", modifier = Modifier.fillMaxWidth())
        }
        else {
            GraphsWidget()
        }
        photoUri?.let { uriString ->
            Image(
                painter = rememberAsyncImagePainter(uriString),
                contentDescription = "Pet Photo",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                navController.navigate(Screen.ManageReports.route.replace("{petId}", petId.toString()))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Manage Reports")
        }
        Button(
            onClick = {
                navController.navigate(Screen.Reports.route.replace("{petId}", petId.toString()))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Reports")
        }
        Button(
            onClick = {
                navController.navigate(Screen.Reminders.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Setup a Reminder")
        }
    }
}