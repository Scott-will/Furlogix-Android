package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.componets.reports.PendingReportsDialog
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.UserViewModel

@Composable
fun DashboardScreen(navController: NavController, userViewModel: UserViewModel = hiltViewModel()) {
    userViewModel.populateCurrentUser()
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