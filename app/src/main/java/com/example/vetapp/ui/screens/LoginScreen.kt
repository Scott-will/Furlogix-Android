package com.example.vetapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.viewmodels.UserViewModel

@Composable
fun LoginScreen(navController: NavController) {
    // State variables to hold the username and password values
    var username by remember { mutableStateOf("") }

    var viewModel: UserViewModel = hiltViewModel()

    // State for handling user existence check
    var userExists by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        viewModel.doesUserExist { exists ->
            userExists = exists
        }
    }

    // Automatically redirect if user existence is determined
    LaunchedEffect(userExists) {
        userExists?.let { exists ->
            if (exists) {
                navController.navigate("dashboard")
            } else {
                navController.navigate("create_account")
            }
        }
    }
}
