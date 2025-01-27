package com.example.vetapp

import ReportCleanerWorker
import android.annotation.SuppressLint
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
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.broadcastreceivers.EmailBroadcastReceiver
import com.example.vetapp.broadcastreceivers.FillOutReportsNotificationReceiver
import com.example.vetapp.broadcastreceivers.SendReportsNotificationReceiver
import com.example.vetapp.ui.AppHeader
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.ui.screens.CreateAccountScreen
import com.example.vetapp.ui.screens.DashboardScreen
import com.example.vetapp.ui.screens.LoginScreen
import com.example.vetapp.ui.screens.ManageReportScreen
import com.example.vetapp.ui.screens.ProfileScreen
import com.example.vetapp.ui.screens.ReportEntryScreen
import com.example.vetapp.ui.screens.ReportTemplateScreen
import com.example.vetapp.ui.screens.ProfileScreen
import com.example.vetapp.ui.screens.RemindersScreen
import com.example.vetapp.ui.screens.ReportEntryHistoryScreen
import com.example.vetapp.ui.screens.ReportsScreen
import com.example.vetapp.ui.components.ActionDialog
import com.example.vetapp.ui.screens.AddPetFormScreen
import com.example.vetapp.ui.screens.UploadPetPhotoScreen
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.UserViewModel

import com.example.vetapp.ui.theme.VetAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity()  {

    private val intentFilter : IntentFilter = IntentFilter()
    private val emailReceiver : EmailBroadcastReceiver = EmailBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //init database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, R.string.database_name.toString()
        ).build()
        //schedule report cleaner
        //TODO: should have handling to warn user it will be deleted if not sent....
        this.scheduleReportCleaner()

        enableEdgeToEdge()
        setContent {
            VetAppTheme {
                VetApp()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initBroadcastReceivers() {

    }

    @SuppressLint("NewApi")
    fun scheduleReportCleaner(){
        val reportCleanerWorkRequest = PeriodicWorkRequest.Builder(
            ReportCleanerWorker::class.java,
            14,
            TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(this.applicationContext)
            .enqueue(reportCleanerWorkRequest)
    }
}

@Composable
fun VetApp(
    userViewModel: UserViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel()
) {
    val userId by userViewModel.userId.collectAsState(initial = 0L)
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
            composable(Screen.Dashboard.route) { DashboardScreen(navController, userViewModel, petViewModel) }
            composable(Screen.ReportsTemplate.route, listOf(navArgument("reportId"){type = NavType.IntType})) { backStackEntry -> val reportId = backStackEntry.arguments?.getInt("reportId")
                if (reportId != null) {
                    ReportTemplateScreen(navController, reportId)
                }
                else{
                    ReportTemplateScreen(navController)
                }
            }
            composable(Screen.ReportEntryHistory.route, listOf(navArgument("reportTemplateId"){type = NavType.IntType})) { backStackEntry -> val reportTemplateId = backStackEntry.arguments?.getInt("reportTemplateId")
                if (reportTemplateId != null) {
                    ReportEntryHistoryScreen(navController, reportTemplateId)
                }
                else{
                    ReportEntryHistoryScreen(navController)
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
                ProfileScreen(userId = userId, navController = navController)
            }
            composable(Screen.ManageReports.route) { ManageReportScreen(navController) }
            composable(Screen.Reports.route) { ReportsScreen(navController) }
            composable(Screen.Reminders.route) { RemindersScreen(navController) }
            composable(
                Screen.AddPet.route,
                arguments = listOf(navArgument("userId") { type = NavType.LongType })
            ) { backStackEntry ->
                AddPetFormScreen(navController = navController, userId = userId)
            }
            composable(Screen.UploadPetPhoto.route) {
                UploadPetPhotoScreen(navController, petViewModel)
            }
        }

        if (showDialog) {
            ActionDialog(
                onDismiss = { showDialog = false },
                onAddPet = {
                    showDialog = false
                    navController.navigate("add_pet/$userId") },
                onViewPets = {
                    showDialog = false
                    navController.navigate("profile/$userId")
                },
                onAddPetPhoto = {
                    showDialog = false
                    navController.navigate("upload_pet_photo") },
                onManageReports = { }
            )
        }
    }
}

