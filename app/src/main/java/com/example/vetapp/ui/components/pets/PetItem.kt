package com.example.vetapp.ui.components.pets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vetapp.Database.Entities.Pet

@Composable
fun PetItem(
    pet: Pet,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = pet.name)
    }
}