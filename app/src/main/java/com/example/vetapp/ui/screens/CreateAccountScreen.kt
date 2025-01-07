package com.example.vetapp.ui.screens

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.Database.Entities.User
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.UserViewModel


@Composable
fun CreateAccountScreen(navController: NavController, viewModel: UserViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Create Profile", fontSize = 24.sp)

        TextField(
            value = viewModel.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Name") },
            isError = viewModel.nameError != null
        )
        if (viewModel.nameError != null) {
            Text(viewModel.nameError!!, color = androidx.compose.ui.graphics.Color.Red)
        }

        TextField(
            value = viewModel.surname,
            onValueChange = viewModel::onSurnameChange,
            label = { Text("Surname") },
            isError = viewModel.surnameError != null
        )
        if (viewModel.surnameError != null) {
            Text(viewModel.surnameError!!, color = androidx.compose.ui.graphics.Color.Red)
        }

        TextField(
            value = viewModel.petName,
            onValueChange = viewModel::onPetNameChange,
            label = { Text("Pet Name") },
            isError = viewModel.petNameError != null
        )
        if (viewModel.petNameError != null) {
            Text(viewModel.petNameError!!, color = androidx.compose.ui.graphics.Color.Red)
        }

        TextField(
            value = viewModel.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            isError = viewModel.emailError != null
        )
        if (viewModel.emailError != null) {
            Text(viewModel.emailError!!, color = androidx.compose.ui.graphics.Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (viewModel.isFormValid()) {
                val user = User(
                    name = viewModel.name,
                    surname = viewModel.surname,
                    petName = viewModel.petName,
                    email = viewModel.email
                )
                viewModel.addUser(user)
                navController.navigate(Screen.Dashboard.route)
            }
        }) {
            Text("Create Profile")
        }
    }
}