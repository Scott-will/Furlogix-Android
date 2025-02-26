package com.example.vetapp

import ReportCleanerWorker
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.broadcastreceivers.EmailBroadcastReceiver
import com.example.vetapp.ui.AppHeader
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.ui.screens.AddPetFormScreen
import com.example.vetapp.ui.screens.CreateAccountScreen
import com.example.vetapp.ui.screens.DashboardScreen
import com.example.vetapp.ui.screens.LoginScreen
import com.example.vetapp.ui.screens.ManageReportScreen
import com.example.vetapp.ui.screens.PetDashboardScreen
import com.example.vetapp.ui.screens.PetsScreen
import com.example.vetapp.ui.screens.ProfileScreen
import com.example.vetapp.ui.screens.RemindersScreen
import com.example.vetapp.ui.screens.ReportEntryHistoryScreen
import com.example.vetapp.ui.screens.ReportEntryScreen
import com.example.vetapp.ui.screens.ReportTemplateScreen
import com.example.vetapp.ui.screens.ReportsScreen
import com.example.vetapp.ui.screens.UploadPetPhotoScreen
import com.example.vetapp.ui.theme.VetAppTheme
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //init database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, R.string.database_name.toString()
        ).build()
        //schedule report cleaner
        this.scheduleReportCleaner()

        enableEdgeToEdge()
        setContent {
            VetAppTheme {
                VetApp()
            }
        }

    }

    @SuppressLint("NewApi")
    fun scheduleReportCleaner() {
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.CreateAccount.route) { CreateAccountScreen(navController) }
            composable(
                Screen.Dashboard.route,
                arguments = listOf(navArgument("userId") { type = NavType.LongType })
            ) {
                DashboardScreen(navController, userId, userViewModel, petViewModel)
            }
            composable(Screen.PetDashboard.route,
                listOf(navArgument("petId") { type = NavType.IntType })
            ) { backStackEntry ->
                val petId = backStackEntry.arguments?.getInt("petId");
                if (petId != null) {
                    PetDashboardScreen(navController, petId, userViewModel, petViewModel)
                }
            }
            composable(
                Screen.ReportsTemplate.route,
                listOf(navArgument("reportId") { type = NavType.IntType },
                    navArgument("reportName") { type = NavType.StringType })
            ) { backStackEntry ->
                val reportId = backStackEntry.arguments?.getInt("reportId");
                val reportName = backStackEntry.arguments?.getString("reportName")
                if (reportId != null && reportName != null) {
                    ReportTemplateScreen(navController, reportId, reportName)
                } else {
                    ReportTemplateScreen(navController)
                }
            }
            composable(
                Screen.ReportEntryHistory.route,
                listOf(navArgument("reportTemplateId") { type = NavType.IntType })
            ) { backStackEntry ->
                val reportTemplateId = backStackEntry.arguments?.getInt("reportTemplateId")
                if (reportTemplateId != null) {
                    ReportEntryHistoryScreen(navController, reportTemplateId)
                } else {
                    ReportEntryHistoryScreen(navController)
                }
            }
            composable(
                Screen.ReportEntry.route,
                listOf(navArgument("reportId") { type = NavType.IntType })
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
            ) { backStackEntry ->
                ProfileScreen(userId = userId, navController = navController)
            }
            composable(
                Screen.ManageReports.route,
                listOf(navArgument("petId") { type = NavType.IntType })
            ) { backStackEntry ->
                val petId = backStackEntry.arguments?.getInt("petId")
                if (petId != null) {
                    ManageReportScreen(navController, petId)
                }
            }
            composable(
                Screen.Reports.route,
                listOf(navArgument("petId") { type = NavType.IntType })
            ) { backStackEntry ->
                val petId = backStackEntry.arguments?.getInt("petId")
                if (petId != null) {
                    ReportsScreen(navController, petId)
                }
            }
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

