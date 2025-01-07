package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.componets.common.NoDataAvailable
import com.example.vetapp.ui.componets.graphs.GraphsWidget
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.ReportViewModel

@Composable
fun DashboardScreen(navController: NavController, reportViewModel: ReportViewModel = hiltViewModel()) {
    reportViewModel.PopulateFavouriteReportTemplates()
    var favouriteReports = reportViewModel.favouriteReportTemplates.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text("Welcome to the Dashboard!")
        if(favouriteReports.value.isEmpty()){
            NoDataAvailable("Favourite Report Templates", modifier = Modifier.fillMaxWidth())
        }
        else{
            GraphsWidget()
        }

        Button(
            onClick = {
                navController.navigate(Screen.ManageReports.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Manage Reports")
        }
        Button(
            onClick = {
                navController.navigate(Screen.Reports.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Reports")
        }
    }
}