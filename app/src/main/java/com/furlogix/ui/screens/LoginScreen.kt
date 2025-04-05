package com.furlogix.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.ui.navigation.Screen
import com.furlogix.viewmodels.UserViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: UserViewModel = hiltViewModel()

    // State for handling user existence check
    var userExists by remember { mutableStateOf<Boolean?>(null) }
    val userId by viewModel.currentUser.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.doesUserExist { exists ->
            userExists = exists
            println("testing: $userExists")
        }
        viewModel.populateCurrentUser()
    }

    LaunchedEffect(userExists, userId) {
        println("userExists: $userExists, userId: $userId")
        when {
            userExists == true && userId != null -> {
                navController.navigate(Screen.Dashboard.createRoute(userId!!.uid.toLong()))
            }
            userExists == false -> {
                navController.navigate(Screen.CreateAccount.route)
            }
        }
    }

    // Fallback UI while waiting for navigation state updates
    androidx.compose.material3.Surface(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            androidx.compose.material3.CircularProgressIndicator()
            androidx.compose.material3.Text(text = "Loading...")
        }
    }
}
