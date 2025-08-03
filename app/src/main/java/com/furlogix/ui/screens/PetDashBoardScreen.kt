package com.furlogix.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val scale by animateFloatAsState(
                        targetValue = 1f,
                        animationSpec = tween(1000),
                        label = "headerScale"
                    )

                    Text(
                        text = "${currentPet.value?.name ?: "Pet"} Dashboard",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.scale(scale)
                    )

                    Text(
                        text = "Manage your pet's health and care",
                        fontSize = 16.sp,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Pet Photo Card
            item {
                photoUri?.let { uriString ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Box {
                            Image(
                                painter = rememberAsyncImagePainter(uriString),
                                contentDescription = "Pet Photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .clip(RoundedCornerShape(24.dp))
                            )

                            // Gradient overlay for better text visibility
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black.copy(alpha = 0.3f)
                                            )
                                        )
                                    )
                            )

                            // Pet name overlay
                            Text(
                                text = currentPet.value?.name ?: "",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(20.dp)
                            )
                        }
                    }
                }
            }

            // Action Cards
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Reports - Full width
                    ModernActionCard(
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
                    ModernActionCard(
                            title = "Reminders",
                            subtitle = "Setup care alerts",
                            icon = Icons.Default.Notifications,
                            gradient = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF43E97B), Color(0xFF38F9D7))
                            ),
                            onClick = { navController.navigate(Screen.Reminders.route) },
                        )


                        ModernActionCard(
                            title = "All Pets",
                            subtitle = "View your pets",
                            icon = Icons.Default.Person,
                            gradient = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFA709A), Color(0xFFFEE140))
                            ),
                            onClick = { navController.navigate("pets/$userId") },
                        )

                    // Help - Full width
                    ModernActionCard(
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

@Composable
private fun ModernActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = tween(150),
        label = "cardScale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = cardScale
                scaleY = cardScale
            }
            .clickable {
                isPressed = true
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            // Gradient background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icon with gradient background
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(gradient),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Text content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            fontSize = 18.sp
                        )
                    )

                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF64748B),
                            fontSize = 14.sp
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(150)
            isPressed = false
        }
    }
}