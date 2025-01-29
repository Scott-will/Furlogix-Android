package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.Database.Entities.Pet
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun AddPetFormScreen(
    navController: NavController,
    userId: Long,
    userViewModel: UserViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel()
) {
    // For controlling input fields
    var petName by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("") }
    val desc by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("Pet Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = petType,
            onValueChange = { petType = it },
            label = { Text("Pet Type") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    // Create a Pet object (adjust fields as needed for your schema)
                    val pet = Pet(
                        name = petName,
                        type = petType,
                        description = desc,
                        userId = userId
                    )
                    petViewModel.addPetObj(pet)

                    // Go back to the previous screen
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}