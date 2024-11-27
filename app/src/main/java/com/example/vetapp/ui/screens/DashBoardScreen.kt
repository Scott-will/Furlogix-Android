package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vetapp.VetApplication
import com.example.vetapp.email.EmailHandler
import com.example.vetapp.email.EmailWrapper
import com.example.vetapp.email.IEmailHandler

@Composable
fun DashboardScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to the Dashboard!")

    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Button(onClick = { SendEmail() }){
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