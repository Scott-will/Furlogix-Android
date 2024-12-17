package com.example.vetapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.vetapp.ui.theme.VetAppTheme
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.broadcastreceivers.EmailBroadcastReceiver
import com.example.vetapp.ui.AppHeader
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.ui.screens.LoginScreen
import com.example.vetapp.ui.screens.CreateAccountScreen
import com.example.vetapp.ui.screens.DashboardScreen
import com.example.vetapp.ui.screens.ManageReportScreen
import com.example.vetapp.ui.screens.ReportEntryScreen
import com.example.vetapp.ui.screens.ReportTemplateScreen
import com.example.vetapp.ui.screens.ProfileScreen
import com.example.vetapp.ui.screens.ReportsScreen
import com.example.vetapp.ui.components.ActionDialog

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()  {

    private val emailReceiver : EmailBroadcastReceiver = EmailBroadcastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //init database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, R.string.database_name.toString()
        ).build()
        //init broadcast receivers
        this.InitBroadcastReceivers()
        enableEdgeToEdge()
        setContent {
            VetAppTheme {
                VetApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(emailReceiver)
    }

    fun InitBroadcastReceivers(){
        //register broadcast receivers
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(
                this.emailReceiver,
                IntentFilter(Intent.ACTION_SENDTO),
                RECEIVER_NOT_EXPORTED
            )
        }
    }
}

@Composable
fun VetApp() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentRoute = currentBackStackEntry?.destination?.route

    println("Current route: $currentRoute")
    println("Dashboard route: ${Screen.Dashboard.route}")

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (currentRoute != null && currentRoute != Screen.Login.route && currentRoute != Screen.CreateAccount.route) {
                AppHeader(navController = navController)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
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
            composable(
                Screen.Profile.route,
                arguments = listOf(navArgument("userId") { type = NavType.LongType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
                ProfileScreen(userId = userId, navController = navController)
            }
            composable(Screen.ManageReports.route) { ManageReportScreen(navController) }
            composable(Screen.Reports.route) { ReportsScreen(navController) }
        }

        if (showDialog) {
            ActionDialog(
                onDismiss = { showDialog = false },
                onAddPet = {  },
                onViewPets = {  },
                onAddPetPhoto = { },
                onManageReports = { }
            )
        }
    }
}

