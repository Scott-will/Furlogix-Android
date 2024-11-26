package com.example.vetapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.UserViewModel
import androidx.compose.runtime.getValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(navController: NavController, viewModel: UserViewModel = hiltViewModel()) {
    val userName by viewModel.userName.collectAsState(initial = "Guest")

    TopAppBar(
        title = { Text("VetApp", color = Color.White) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Dashboard.route) { inclusive = true }
                }
            }) {
                Icon(Icons.Filled.Home, contentDescription = "Go to Dashboard")
            }
            val currentBackStackEntry = navController.currentBackStackEntry
            val currentRoute = currentBackStackEntry?.destination?.route

            if (currentRoute != Screen.Dashboard.route) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Back arrow icon
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = userName, color = Color.White)
                IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "User Profile",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF4A148C)
        )
    )
}

