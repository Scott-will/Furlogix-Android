package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.vetapp.Database.Entities.Pet
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.UserViewModel

@Composable
fun ProfileScreen(
    userId: Long,
    viewModel: UserViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel(),
    navController: NavController
) {
    val userName by viewModel.userName.collectAsState(initial = "")
    val userEmail by viewModel.userEmail.collectAsState(initial = "")

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    //val pets by petViewModel.pets.collectAsState(initial = emptyList())
    var selectedPet by remember { mutableStateOf<Pet?>(null) }

    val pets by petViewModel.pets.collectAsState()

    LaunchedEffect(userId) {
        petViewModel.loadPetsForUser(userId)
    }

    name = userName
    email = userEmail

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        //verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Profile", fontSize = 24.sp)

        // Name Text Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Email Text Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        // Save Button
        Button(
            onClick = {
                viewModel.updateUserProfile(name, email)
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }

        Text(text = "Pets", fontSize = 20.sp, modifier = Modifier.padding(top = 16.dp))

        // Display pet names
        pets.forEach { pet ->
            Button(
                onClick = { selectedPet = pet },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = pet.name)
            }
        }
    }

    if (selectedPet != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { selectedPet = null },
            title = { Text(text = "Pet Details") },
            text = { Text(text = "Type: ${selectedPet?.type}") },
            confirmButton = {
                Button(onClick = { selectedPet = null }) {
                    Text("Close")
                }
            }
        )
    }
}