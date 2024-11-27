package com.example.vetapp.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vetapp.VetApplication
import com.example.vetapp.email.EmailHandler
import com.example.vetapp.email.EmailWrapper
import com.example.vetapp.email.IEmailHandler

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
                // I'm not quite sure what reportId is for
                val reportId = 1
                navController.navigate("reports_template/$reportId")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reports")
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Button(onClick = {SendEmail()}){
            Text("Send Reports")
        }
    }


}

fun SendEmail(){
    val emailHandler: IEmailHandler = EmailHandler(VetApplication.applicationContext())
    val wrapper: EmailWrapper = EmailWrapper(
        "placeholder@gmail.com",
        "placeholder@gmail.com",
        "test",
        "test"
    )
    emailHandler.CreateAndSendEmail(wrapper)
}