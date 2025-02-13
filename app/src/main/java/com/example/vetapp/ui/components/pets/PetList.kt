package com.example.vetapp.ui.components.pets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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