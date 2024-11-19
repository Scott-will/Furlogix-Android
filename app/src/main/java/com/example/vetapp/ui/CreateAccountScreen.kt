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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.CreateProfileViewModel


@Composable
fun CreateAccountScreen(navController: NavController) {
    val viewModel: CreateProfileViewModel = hiltViewModel()
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var petName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Create Profile", fontSize = 24.sp)
        TextField(
            value = name, onValueChange = { name = it }, label = { Text("Name") }
        )
        TextField(
            value = surname, onValueChange = { surname = it }, label = { Text("Surname") }
        )
        TextField(
            value = petName, onValueChange = { petName = it }, label = { Text("Pet Name") }
        )
        TextField(
            value = email, onValueChange = { email = it }, label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.addUser(name, surname, petName, email)
            navController.navigate(Screen.Dashboard.route)
        }) {
            Text("Create Profile")
        }
    }
}