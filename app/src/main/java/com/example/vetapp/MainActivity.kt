package com.example.vetapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.vetapp.ui.theme.VetAppTheme
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.broadcastreceivers.EmailBroadcastReceiver
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.ui.screens.LoginScreen
import com.example.vetapp.ui.screens.CreateAccountScreen
import com.example.vetapp.ui.screens.DashboardScreen
import com.example.vetapp.ui.screens.ReportEntryScreen
import com.example.vetapp.ui.screens.ReportScreen
import com.example.vetapp.ui.screens.ReportTemplateScreen
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
    NavHost(navController = navController, startDestination = Screen.Reports.route) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.CreateAccount.route) { CreateAccountScreen(navController) }
        composable(Screen.Dashboard.route) { DashboardScreen() }
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
        composable(Screen.Reports.route) { ReportScreen(navController) }
    }
}