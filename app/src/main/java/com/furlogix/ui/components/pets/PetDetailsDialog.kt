package com.furlogix.ui.components.pets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.compose.rememberAsyncImagePainter
import com.furlogix.Database.Entities.Pet

@Composable
fun PetDetailsDialog(
    pet: Pet,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Pet Details") },
        text = {
            Column {
                pet.photoUri?.let { uriString ->
                    Image(
                        painter = rememberAsyncImagePainter(uriString),
                        contentDescription = "Pet Photo",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text("Type: ${pet.type}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        },
        dismissButton = {
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("Delete")
            }
        }
    )
}