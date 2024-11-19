package com.example.vetapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.vetapp.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(navController: NavController) {
    TopAppBar(
        title = { Text("VetApp", color = Color.White) },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(Screen.Dashboard.route) }) {
                Icon(Icons.Filled.Home, contentDescription = "Go to Dashboard")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF4A148C)
        )
    )
}