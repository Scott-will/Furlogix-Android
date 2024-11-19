package com.example.vetapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vetapp.ui.navigation.Screen


@Composable
fun CreateAccountScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Create Profile", fontSize = 24.sp)
        TextField(
            value = "", onValueChange = {}, label = { Text("Name") }
        )
        TextField(
            value = "", onValueChange = {}, label = { Text("Surname") }
        )
        TextField(
            value = "", onValueChange = {}, label = { Text("Pet Name") }
        )
        TextField(
            value = "", onValueChange = {}, label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.Dashboard.route) }) {
            Text("Create Profile")
        }
    }
}