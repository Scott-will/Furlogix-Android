package com.example.vetapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vetapp.ui.AppHeader
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.ui.screens.AddPetFormScreen
import com.example.vetapp.ui.screens.CreateAccountScreen
import com.example.vetapp.ui.screens.DashboardScreen
import com.example.vetapp.ui.screens.EditReportScreen
import com.example.vetapp.ui.screens.LoginScreen
import com.example.vetapp.ui.screens.ManageReportScreen
import com.example.vetapp.ui.screens.PetDashboardScreen
import com.example.vetapp.ui.screens.PetsScreen
import com.example.vetapp.ui.screens.ProfileScreen
import com.example.vetapp.ui.screens.RemindersScreen
import com.example.vetapp.ui.screens.ReportEntryHistoryScreen
import com.example.vetapp.ui.screens.ReportEntryScreen
import com.example.vetapp.ui.screens.UploadPetPhotoScreen
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.UserViewModel

@Composable
fun ComposeNavGraph(
    navController: NavHostController = rememberNavController(),
    userViewModel: UserViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel(),
    innerPadding: Modifier = Modifier
) {
    // You can collect any state you need for navigation
    val userId by userViewModel.userId.collectAsState(initial = 0L)

    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(
        initial = navController.currentBackStackEntry
    )
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            // Hide header on Login and CreateAccount screens
            println("current route: $currentRoute")
            if (currentRoute != null && currentRoute != Screen.Login.route && currentRoute != Screen.CreateAccount.route) {
                AppHeader(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.CreateAccount.route) { CreateAccountScreen(navController) }
            composable(
                Screen.Dashboard.route,
                arguments = listOf(navArgument("userId") { type = NavType.LongType })
            ) {
                DashboardScreen(navController, userId, userViewModel, petViewModel)
            }
            composable(
                Screen.PetDashboard.route,
                arguments = listOf(navArgument("petId") { type = NavType.IntType })
            ) { backStackEntry ->
                val petId = backStackEntry.arguments?.getInt("petId")
                if (petId != null) {
                    PetDashboardScreen(navController, petId, userViewModel, petViewModel)
                }
            }
            composable(
                Screen.EditReport.route,
                arguments = listOf(navArgument("reportId") { type = NavType.IntType })
            ) { backStackEntry ->
                val reportId = backStackEntry.arguments?.getInt("reportId")
                if (reportId != null) {
                    EditReportScreen(reportId)
                }
            }
            composable(
                Screen.ReportEntryHistory.route,
                arguments = listOf(navArgument("reportId") { type = NavType.IntType })
            ) { backStackEntry ->
                val reportId = backStackEntry.arguments?.getInt("reportId")
                if (reportId != null) {
                    ReportEntryHistoryScreen(navController, reportId)
                } else {
                    ReportEntryHistoryScreen(navController)
                }
            }
            composable(
                Screen.ReportEntry.route,
                arguments = listOf(navArgument("reportId") { type = NavType.IntType })
            ) { backStackEntry ->
                val reportId = backStackEntry.arguments?.getInt("reportId")
                if (reportId != null) {
                    ReportEntryScreen(navController, reportId)
                } else {
                    ReportEntryScreen(navController)
                }
            }
            composable(
                Screen.Profile.route,
                arguments = listOf(navArgument("userId") { type = NavType.LongType })
            ) {
                ProfileScreen(userId = userId, navController = navController)
            }
            composable(
                Screen.ManageReports.route,
                arguments = listOf(navArgument("petId") { type = NavType.IntType })
            ) { backStackEntry ->
                val petId = backStackEntry.arguments?.getInt("petId")
                if (petId != null) {
                    ManageReportScreen(navController, petId)
                }
            }
            composable(Screen.Reminders.route) { RemindersScreen(navController) }
            composable(
                Screen.AddPet.route,
                arguments = listOf(navArgument("userId") { type = NavType.LongType })
            ) {
                AddPetFormScreen(navController = navController, userId = userId)
            }
            composable(Screen.UploadPetPhoto.route) {
                UploadPetPhotoScreen(navController, petViewModel)
            }
            composable(
                Screen.Pets.route,
                arguments = listOf(navArgument("userId") { type = NavType.LongType })
            ) { backStackEntry ->
                val usrId = backStackEntry.arguments?.getLong("userId") ?: 0L
                PetsScreen(navController = navController, userId = usrId)
            }
        }
    }
}