package com.example.vetapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.UserViewModel


@Composable
fun DashboardScreen(navController: NavController, userId : Long, userViewModel: UserViewModel = hiltViewModel(), petViewModel : PetViewModel = hiltViewModel()) {

    val pets by petViewModel.pets.collectAsState()

    LaunchedEffect(userId) {
        if (userId != 0L) {
            petViewModel.loadPetsForUser(userId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){

        Text(text = "Pets", fontSize = 20.sp, modifier = Modifier.padding(top = 16.dp))

        pets.forEach { pet ->
            Button(
                onClick = { navController.navigate(Screen.PetDashboard.route.replace("{petId}", pet.uid.toString())) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4A148C)
                ),
                border = BorderStroke(2.dp, Color(0xFF4A148C)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = pet.name)
            }
        }
    }
}