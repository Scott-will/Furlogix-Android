package com.furlogix.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.Database.Entities.Pet
import com.furlogix.ui.components.pets.ConfirmDeleteDialog
import com.furlogix.ui.components.pets.PetDetailsDialog
import com.furlogix.ui.components.pets.PetList
import com.furlogix.viewmodels.PetViewModel

@Composable
fun PetsScreen(
    navController: NavController,
    userId: Long,
    petViewModel: PetViewModel = hiltViewModel()
) {
    LaunchedEffect(userId) {
        petViewModel.loadPetsForUser(userId)
    }

    val pets by petViewModel.pets.collectAsState(initial = emptyList())
    var selectedPet by remember { mutableStateOf<Pet?>(null) }
    var showConfirmDelete by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("add_pet/$userId") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Add Pet")
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        Text(
            text = "My Pets",
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                PetList(pets = pets, onPetSelected = { pet ->
                    selectedPet = pet
                })
            }
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
