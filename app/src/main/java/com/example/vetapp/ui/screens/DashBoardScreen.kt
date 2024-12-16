package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vetapp.ui.navigation.Screen

@Composable
fun DashboardScreen(navController: NavController) {
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
        Button(
            onClick = {
                navController.navigate(Screen.Reports.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Setup a Reminder")
        }
    }
}