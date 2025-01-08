package com.example.vetapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionDialog(
    onDismiss: () -> Unit,
    onAddPet: () -> Unit,
    onViewPets: () -> Unit,
    onAddPetPhoto: () -> Unit,
    onManageReports: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDismiss() },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onAddPet,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A148C),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()) {
                    Text("Add Pet")
                }
                Button(onClick = onViewPets,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A148C),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()) {
                    Text("View Pets")
                }
                Button(
                    onClick = onAddPetPhoto,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A148C),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()) {
                    Text("Upload Pet Photo")
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A148C),
                    contentColor = Color.White
                )) {
                Text("Close")
            }
        }
    )
}