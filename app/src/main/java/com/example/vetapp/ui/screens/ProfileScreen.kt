package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.Database.Entities.Pet
import com.example.vetapp.ui.components.pets.ConfirmDeleteDialog
import com.example.vetapp.ui.components.pets.PetDetailsDialog
import com.example.vetapp.ui.components.pets.PetList
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.UserViewModel


@Composable
fun ProfileScreen(
    userId: Long,
    viewModel: UserViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel(),
    navController: NavController
) {
    val userName by viewModel.userName.collectAsState(initial = "Guest")
    val userEmail by viewModel.userEmail.collectAsState(initial = "example@example.com")

    var name by remember { mutableStateOf(userName.ifBlank { "Guest" }) }
    var email by remember { mutableStateOf(userEmail.ifBlank { "example@example.com" }) }
    var selectedPet by remember { mutableStateOf<Pet?>(null) }
    var showConfirmDelete by remember { mutableStateOf(false) }
    val pets by petViewModel.pets.collectAsState()
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        if (userId != 0L) {
            petViewModel.loadPetsForUser(userId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Profile", fontSize = 24.sp)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

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

        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            item {
                PetList(pets = pets, onPetSelected = { pet ->
                    selectedPet = pet
                })
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        Button(
            onClick = { showDeleteConfirmation = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Delete Account")
        }

        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Delete Account") },
                text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteUser(userId)
                            navController.navigate(Screen.CreateAccount.route) {
                                popUpTo(0)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        )
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDeleteConfirmation = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

    if (selectedPet != null && !showConfirmDelete) {
        PetDetailsDialog(
            pet = selectedPet!!,
            onDismiss = { selectedPet = null },
            onDelete = { showConfirmDelete = true }
        )
    }

    if (showConfirmDelete && selectedPet != null) {
        ConfirmDeleteDialog(
            pet = selectedPet!!,
            onConfirm = {
                petViewModel.deletePet(selectedPet!!)
                showConfirmDelete = false
                selectedPet = null
            },
            onDismiss = { showConfirmDelete = false }
        )
    }
}
