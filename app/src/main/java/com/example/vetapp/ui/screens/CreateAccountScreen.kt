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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.Database.Entities.User
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.UserViewModel


@Composable
fun CreateAccountScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel()
    ) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Create Profile", fontSize = 24.sp)

        TextField(
            value = userViewModel.name,
            onValueChange = userViewModel::onNameChange,
            label = { Text("Name") },
            isError = userViewModel.nameError != null
        )
        if (userViewModel.nameError != null) {
            Text(userViewModel.nameError!!, color = androidx.compose.ui.graphics.Color.Red)
        }

        TextField(
            value = userViewModel.surname,
            onValueChange = userViewModel::onSurnameChange,
            label = { Text("Surname") },
            isError = userViewModel.surnameError != null
        )
        if (userViewModel.surnameError != null) {
            Text(userViewModel.surnameError!!, color = androidx.compose.ui.graphics.Color.Red)
        }

        val petName = petViewModel.name.collectAsState().value
        val petType = petViewModel.type.collectAsState().value
        //val petNameError = petViewModel.nameError.collectAsState().value

        TextField(
            value = petName,
            onValueChange = { petViewModel.name.value = it },
            label = { Text("Pet Name") },
            //isError = petNameError != null
        )
        /*
        if (petNameError != null) {
            Text(
                text = petNameError,
                color = androidx.compose.ui.graphics.Color.Red
            )
        }
        */


        TextField(
            value = petType,
            onValueChange = { petViewModel.type.value = it },
            label = { Text("Pet Type") }
        )
        /*
        if (petViewModel.petNameError != null) {
            Text(petViewModel.petNameError!!, color = androidx.compose.ui.graphics.Color.Red)
        }
        */


        TextField(
            value = userViewModel.email,
            onValueChange = userViewModel::onEmailChange,
            label = { Text("Email") },
            isError = userViewModel.emailError != null
        )
        if (userViewModel.emailError != null) {
            Text(userViewModel.emailError!!, color = androidx.compose.ui.graphics.Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (userViewModel.isFormValid() && petViewModel.validateForm()) {
                val user = User(
                    name = userViewModel.name,
                    surname = userViewModel.surname,
                    email = userViewModel.email
                )

                userViewModel.addUser(user) { userId ->
                    petViewModel.addPet(userId = userId)
                    navController.navigate(Screen.Dashboard.route)
                }

            }
        }) {
            Text("Create Profile")
        }
    }
}