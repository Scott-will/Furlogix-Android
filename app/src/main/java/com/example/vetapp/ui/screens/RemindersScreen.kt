package com.example.vetapp.ui.screens

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vetapp.VetApplication
import com.example.vetapp.ui.componets.ReportsReminder
import com.example.vetapp.viewmodels.RemindersViewModel


@Composable
fun RemindersScreen(navController: NavController, viewModel: RemindersViewModel = hiltViewModel()) {
    val permission = Manifest.permission.SCHEDULE_EXACT_ALARM
    var isPermissionGranted by remember { mutableStateOf(viewModel.checkAndRequestExactAlarmPermission()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        if(isPermissionGranted){
            ReportsReminder(viewModel)
        }
        else{
            Button(
                onClick = {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).setFlags(FLAG_ACTIVITY_NEW_TASK)
                    startActivity(VetApplication.applicationContext(), intent, null) },
                content = { Text("Enable Permissions") }
            )
        }


    }
}
