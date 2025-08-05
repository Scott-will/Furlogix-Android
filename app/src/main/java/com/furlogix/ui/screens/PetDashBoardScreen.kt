package com.furlogix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.ui.components.common.ActionCard
import com.furlogix.ui.components.common.Header
import com.furlogix.ui.components.pets.PetPhotoCard
import com.furlogix.ui.components.reports.PendingReportsDialog
import com.furlogix.ui.help.HelpWizard
import com.furlogix.ui.navigation.Screen
import com.furlogix.viewmodels.PetViewModel
import com.furlogix.viewmodels.ReportViewModel
import com.furlogix.viewmodels.UserViewModel

@Composable
fun PetDashboardScreen(
    navController: NavController,
    petId: Int,
    userViewModel: UserViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel(),
    reportViewModel: ReportViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        userViewModel.populateCurrentUser()
        petViewModel.populateCurrentPet(petId)
    }

    val photoUri by petViewModel.photoUri.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()
    val userId by userViewModel.userId.collectAsState(initial = 0L)
    val currentPet = petViewModel.currentPet.collectAsState()
    var showHelp by remember { mutableStateOf(false) }

    if (currentUser != null && currentUser!!.pendingSentReports) {
        PendingReportsDialog(
            onConfirm = { userViewModel.setNoPendingReportsForUser() },
            onDismiss = { navController.navigate(Screen.Reports.route.replace("{petId}", petId.toString())) }
        )
    }

    if (showHelp) {
        HelpWizard(
            onFinish = { showHelp = false }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8F9FF),
                        Color(0xFFEEF2FF),
                        Color(0xFFE0E7FF)
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            // Header Section
            item {
                Header("${currentPet.value?.name ?: "Pet"} Dashboard",
                    "Manage your pet's health and care")
            }

            // Pet Photo Card
            item {
                photoUri?.let { uriString ->
                    PetPhotoCard(uriString, currentPet.value?.name ?: "")
                }
            }

            // Action Cards
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionCard(
                        title = "Health Reports",
                        subtitle = "Track and manage pet health",
                        icon = Icons.Default.DateRange,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                        ),
                        onClick = {
                            navController.navigate(Screen.ManageReports.route.replace("{petId}", petId.toString()))
                        }
                    )
                    ActionCard(
                            title = "Reminders",
                            subtitle = "Setup care alerts",
                            icon = Icons.Default.Notifications,
                            gradient = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF43E97B), Color(0xFF38F9D7))
                            ),
                            onClick = { navController.navigate(Screen.Reminders.route) },
                        )


                    ActionCard(
                            title = "All Pets",
                            subtitle = "View your pets",
                            icon = Icons.Default.Person,
                            gradient = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFA709A), Color(0xFFFEE140))
                            ),
                            onClick = { navController.navigate("pets/$userId") },
                        )

                    ActionCard(
                        title = "Need Help?",
                        subtitle = "Get assistance and tutorials",
                        icon = Icons.Default.Info,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                        ),
                        onClick = { showHelp = true }
                    )
                }
            }
        }
    }
}

