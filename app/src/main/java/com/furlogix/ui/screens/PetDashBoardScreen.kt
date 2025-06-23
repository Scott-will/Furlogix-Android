package com.furlogix.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.furlogix.ui.components.reports.PendingReportsDialog
import com.furlogix.ui.help.HelpWizard
import com.furlogix.ui.navigation.Screen
import com.furlogix.ui.theme.ButtonBlue
import com.furlogix.viewmodels.PetViewModel
import com.furlogix.viewmodels.ReportViewModel
import com.furlogix.viewmodels.UserViewModel


@Composable
fun PetDashboardScreen(navController: NavController, petId : Int, userViewModel: UserViewModel = hiltViewModel(), petViewModel : PetViewModel = hiltViewModel(), reportViewModel: ReportViewModel = hiltViewModel()) {
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

    if(showHelp){
        HelpWizard(
            onFinish = {showHelp = false}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "${currentPet.value?.name} Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        photoUri?.let { uriString ->
            androidx.compose.material3.Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(uriString),
                    contentDescription = "Pet Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }

        val buttonModifier = Modifier
            .weight(1f)
            .height(56.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.ManageReports.route.replace("{petId}", petId.toString())) },
                    shape = RoundedCornerShape(20.dp),
                    modifier = buttonModifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBlue,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        "Reports",
                        fontSize = 17.sp
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.Reminders.route) },
                    shape = RoundedCornerShape(20.dp),
                    modifier = buttonModifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBlue,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        "Setup Reminders",
                        fontSize = 17.sp
                    )
                }
                Button(
                    onClick = { navController.navigate("pets/$userId") },
                    shape = RoundedCornerShape(20.dp),
                    modifier = buttonModifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBlue,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        "Pets",
                        fontSize = 17.sp
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Button(
                    onClick = {showHelp = true},
                    shape = RoundedCornerShape(20.dp),
                    modifier = buttonModifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBlue,
                        contentColor = Color.Black
                    )
                ){
                    Text(
                        "Help",
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}