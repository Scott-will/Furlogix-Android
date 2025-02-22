package com.example.vetapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.UserViewModel

@Composable
fun BottomNavigationBar(navController: NavController, userViewModel: UserViewModel = hiltViewModel()) {
    val items = listOf("Pets", "Reports", "Reminders")
    val userId by userViewModel.userId.collectAsState(initial = 0L)

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavButton(navController, "Home", Screen.Dashboard.route.replace("{userId}", userId.toString()))
            BottomNavButton(navController, "Reminders", Screen.Reminders.route)
            //FabWithVerticalMenu()
        }
    }
}

@Composable
fun BottomNavButton(navController: NavController, label: String, route: String) {
    IconButton(onClick = { navController.navigate(route) }) {
        Text(label, color = Color.Black)
    }
}
@Composable
fun FabWithVerticalMenu() {
    var isMenuOpen by remember { mutableStateOf(false) }
    val items = listOf(
        "Item 1" to "Description 1",
        "Item 2" to "Description 2",
        "Item 3" to "Description 3"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // Close the menu if tapped outside
                    if (isMenuOpen) {
                        isMenuOpen = false
                    }
                }
            },
        contentAlignment = Alignment.BottomEnd // Align FAB at bottom-right corner
    ) {
        // Floating Action Button (FAB)
        FloatingActionButton(
            onClick = { isMenuOpen = !isMenuOpen },
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Open Menu", tint = Color.White)
        }

        // If the menu is open, show the vertical menu directly above the FAB
        if (isMenuOpen) {
            VerticalMenu(items = items)
        }
    }
}

@Composable
fun VerticalMenu(items: List<Pair<String, String>>) {
    Box(
        modifier = Modifier
            //.fillMaxSize() // Take up the full screen size
            .padding(bottom = 72.dp), // Keep distance from the FAB
        contentAlignment = Alignment.BottomEnd // Align content to bottom-right corner
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                //.verticalScroll(rememberScrollState()) // Optional: makes menu scrollable
        ) {
            items.forEach { item ->
                MenuItem(item)
                Spacer(modifier = Modifier.height(8.dp)) // Spacer between items
            }
        }
    }
}

@Composable
fun MenuItem(item: Pair<String, String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            //.fillMaxWidth()
            .clickable { /* Handle item click */ }
            .padding(8.dp)
    ) {
        Icon(Icons.Default.Person, contentDescription = item.first, tint = Color.Black)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = item.first, fontWeight = FontWeight.Bold)
            Text(text = item.second, style = MaterialTheme.typography.bodySmall)
        }
    }
}
