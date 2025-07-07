package com.furlogix.ui.components.pets

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.furlogix.Database.Entities.Pet
import com.furlogix.ui.screens.copyUriToInternalStorage
import com.furlogix.viewmodels.PetViewModel

@Composable
fun PetDetailsDialog(
    pet: Pet,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    petViewModel: PetViewModel = hiltViewModel()
) {
    val isNewPet = pet.uid == 0
    var name by remember { mutableStateOf(pet.name) }
    var type by remember { mutableStateOf(pet.type) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            // Copy the picked file to internal storage
            val localUri = copyUriToInternalStorage(context, uri, "pet_${System.currentTimeMillis()}.jpg")
            selectedImageUri = localUri
        }
    }
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Pet Details") },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (pet.photoUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(pet.photoUri),
                            contentDescription = "Pet Photo",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )

                        TextButton(
                            onClick = { imagePickerLauncher.launch("image/*") },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
                        ) {
                            Text("Edit Photo", color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    } else {
                        OutlinedButton(
                            onClick = { imagePickerLauncher.launch("image/*") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                        ) {
                            Text("Add Photo")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Type") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    singleLine = true
                )
            }

        },
        confirmButton = {
            val isValid = name.isNotBlank() && type.isNotBlank()
            Button(onClick = {
                onDismiss();
                val updatedPet = pet.copy(
                    name = name,
                    type = type,
                    photoUri = selectedImageUri?.toString() ?: pet.photoUri
                )
                if (isNewPet) {
                    petViewModel.addPetObj(updatedPet)
                } else {
                    petViewModel.updatePet(updatedPet)
                }
            },
                enabled = isValid) {
                Text("Save")
            }
        },
        dismissButton = {
            val dismissText = if (isNewPet) "Close" else "Delete"
            val dismissAction = if (isNewPet) onDismiss else onDelete

            Button(
                onClick = dismissAction,
                colors = if (isNewPet) ButtonDefaults.buttonColors()
                else ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(dismissText)
            }
        }

    )
}