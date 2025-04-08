package com.furlogix.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.furlogix.Database.Entities.Pet
import com.furlogix.Database.Entities.User
import com.furlogix.ui.navigation.Screen
import com.furlogix.viewmodels.PetViewModel
import com.furlogix.viewmodels.UserViewModel


@Composable
fun CreateAccountScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel()
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Copy the picked file to internal storage
            selectedImageUri = copyUriToInternalStorage(
                context,
                it,
                "pet_${System.currentTimeMillis()}.jpg"
            )
        }
    }

    // Wrapping content in a Box with a background and scrollable Column for a modern feel.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Create Profile",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = userViewModel.name,
                onValueChange = userViewModel::onNameChange,
                label = { Text("Name") },
                isError = userViewModel.nameError != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (userViewModel.nameError != null) {
                Text(
                    text = userViewModel.nameError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = userViewModel.surname,
                onValueChange = userViewModel::onSurnameChange,
                label = { Text("Surname") },
                isError = userViewModel.surnameError != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (userViewModel.surnameError != null) {
                Text(
                    text = userViewModel.surnameError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = userViewModel.email,
                onValueChange = userViewModel::onEmailChange,
                label = { Text("Email") },
                isError = userViewModel.emailError != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (userViewModel.emailError != null) {
                Text(
                    text = userViewModel.emailError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            val petName by petViewModel.name.collectAsState()
            OutlinedTextField(
                value = petName,
                onValueChange = { petViewModel.name.value = it },
                label = { Text("Pet Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            val petType by petViewModel.type.collectAsState()
            OutlinedTextField(
                value = petType,
                onValueChange = { petViewModel.type.value = it },
                label = { Text("Pet Type") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Upload Pet Photo")
            }

            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Uploaded Pet Photo",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Text(
                    text = "Photo uploaded successfully!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = {
                    if (userViewModel.isFormValid() && petViewModel.validateForm()) {
                        val user = User(
                            name = userViewModel.name,
                            surname = userViewModel.surname,
                            email = userViewModel.email
                        )

                        userViewModel.addUser(user) { userId ->
                            val pet = Pet(
                                name = petName,
                                type = petType,
                                description = "",
                                userId = userId,
                                photoUri = selectedImageUri?.toString()
                            )
                            petViewModel.addPetObj(pet)
                            navController.navigate(Screen.Dashboard.createRoute(userId))
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Create Profile",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
