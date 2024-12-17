package com.example.vetapp

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.vetapp.ui.theme.VetAppTheme
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.broadcastreceivers.EmailBroadcastReceiver
import com.example.vetapp.broadcastreceivers.FillOutReportsNotificationReceiver
import com.example.vetapp.broadcastreceivers.SendReportsNotificationReceiver
import com.example.vetapp.ui.AppHeader
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.ui.screens.LoginScreen
import com.example.vetapp.ui.screens.CreateAccountScreen
import com.example.vetapp.ui.screens.DashboardScreen
import com.example.vetapp.ui.screens.ManageReportScreen
import com.example.vetapp.ui.screens.ReportEntryScreen
import com.example.vetapp.ui.screens.ReportTemplateScreen
import com.example.vetapp.ui.screens.ProfileScreen
import com.example.vetapp.ui.screens.RemindersScreen
import com.example.vetapp.ui.screens.ReportsScreen

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //init database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, R.string.database_name.toString()
        ).build()
        enableEdgeToEdge()
        setContent {
            VetAppTheme {
                VetApp()
            }
        }
        requestPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun requestPermissions() {

    }
}

@Composable
fun VetApp() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = currentBackStackEntry?.destination?.route

    println("Current route: $currentRoute")
    println("Dashboard route: ${Screen.Dashboard.route}")

    Scaffold(
        topBar = {
            if (currentRoute != null && currentRoute != Screen.Login.route && currentRoute != Screen.CreateAccount.route) {
                AppHeader(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.CreateAccount.route) { CreateAccountScreen(navController) }
            composable(Screen.Dashboard.route) { DashboardScreen(navController) }
            composable(Screen.ReportsTemplate.route, listOf(navArgument("reportId"){type = NavType.IntType})) { backStackEntry -> val reportId = backStackEntry.arguments?.getInt("reportId")
                if (reportId != null) {
                    ReportTemplateScreen(navController, reportId)
                }
                else{
                    ReportTemplateScreen(navController)
                }
            }
            composable(Screen.ReportEntry.route, listOf(navArgument("reportId"){type = NavType.IntType})) { backStackEntry -> val reportId = backStackEntry.arguments?.getInt("reportId")
                if (reportId != null) {
                    ReportEntryScreen(navController, reportId)
                }
                else{
                    ReportEntryScreen(navController)
                }
            }
            composable(Screen.Profile.route) { ProfileScreen(navController = navController) }
            composable(Screen.ManageReports.route) { ManageReportScreen(navController) }
            composable(Screen.Reports.route) { ReportsScreen(navController) }
            composable(Screen.Reminders.route) { RemindersScreen(navController) }
        }
    }
}