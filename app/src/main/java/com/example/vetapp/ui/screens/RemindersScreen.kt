package com.example.vetapp.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.VetApplication
import com.example.vetapp.ui.componets.ReportsReminder
import com.example.vetapp.viewmodels.RemindersViewModel
import com.example.vetapp.viewmodels.UserViewModel
import kotlin.contracts.contract

@Composable
fun RemindersScreen(navController: NavController, viewModel: RemindersViewModel = hiltViewModel(),) {
    val permission = Manifest.permission.SCHEDULE_EXACT_ALARM
    var isPermissionGranted by remember { mutableStateOf(checkPermissionFor(permission)) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        if(isPermissionGranted){
            ReportsReminder(viewModel)
        }
        else{
            val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = { isGranted ->
                if(isGranted){
                    isPermissionGranted = true
                }
            })
            if(!isPermissionGranted){
                Button(
                    onClick = { launcher.launch(permission) },
                    content = { Text("Enable Permissions") }
                )

            }
        }


    }
}

private fun checkPermissionFor(permission : String) : Boolean{
    return ContextCompat.checkSelfPermission(VetApplication.applicationContext(), permission) == PackageManager.PERMISSION_GRANTED
}