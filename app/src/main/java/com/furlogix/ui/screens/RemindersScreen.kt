package com.furlogix.ui.screens

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.R
import com.furlogix.Furlogix
import com.furlogix.ui.components.reminders.RemindersList
import com.furlogix.ui.components.reminders.ReportsReminder
import com.furlogix.viewmodels.RemindersViewModel


@Composable
fun RemindersScreen(navController: NavController, viewModel: RemindersViewModel = hiltViewModel()) {
    val isPermissionGranted by remember { mutableStateOf(viewModel.checkAndRequestExactAlarmPermission()) }
    val reminders = viewModel.reminders.collectAsState()
    var addReminder by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Image(
            painter = painterResource(R.drawable.clock),
            contentDescription = "My Custom Image",
            modifier = Modifier.size(200.dp),

            )
        RemindersList(reminders.value,onDeleteClick = {item -> viewModel.deleteReminder(item)})

        if(isPermissionGranted){
            Button(onClick = {addReminder = true}) {
                Text("Add Reminder")
            }
            if(addReminder){
                ReportsReminder({addReminder = false}, viewModel)
            }

        }
        else{
            Button(
                onClick = {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).setFlags(FLAG_ACTIVITY_NEW_TASK)
                    startActivity(Furlogix.applicationContext(), intent, null) },
                content = { Text("Enable Permissions") }
            )
        }


    }
}
