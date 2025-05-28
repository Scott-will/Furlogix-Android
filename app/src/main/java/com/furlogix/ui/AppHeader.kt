package com.furlogix.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.ui.navigation.Screen
import com.furlogix.viewmodels.UserViewModel
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(navController: NavController, userViewModel: UserViewModel = hiltViewModel()) {
    val userNameFlow = userViewModel.userName.map { it ?: "Guest" }
    val userName by userNameFlow.collectAsState(initial = "Guest")
    val userId by userViewModel.userId.collectAsState(initial = 0L)

    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = currentBackStackEntry?.destination?.route ?: Screen.Dashboard.route

    println("Current route: $currentRoute")
    println("Dashboard route: ${Screen.Dashboard.route}")

    TopAppBar(
        title = { Text("Furlogix", color = Color.White) },
        navigationIcon = {
            if (currentRoute != Screen.Dashboard.route) { // Only show back button if not on Dashboard
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            } else {
                IconButton(onClick = {
                    navController.navigate(Screen.Dashboard.createRoute(userId)) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }) {
                    Icon(Icons.Filled.Home, contentDescription = "Go to Dashboard", tint = Color.White)
                }
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = userName, color = Color.White)
                IconButton(onClick = { navController.navigate("profile/$userId") }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "User Profile",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        )
    )
}

