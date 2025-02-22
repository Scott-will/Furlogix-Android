package com.example.vetapp.ui.components.pets

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vetapp.Database.Entities.Pet

@Composable
fun PetList(
    pets: List<Pet>,
    onPetSelected: (Pet) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        pets.forEach { pet ->
            PetItem(
                pet = pet,
                onClick = { onPetSelected(pet) }
            )
        }
    }
}