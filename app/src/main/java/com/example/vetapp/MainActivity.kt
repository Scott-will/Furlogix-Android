package com.example.vetapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.vetapp.ui.theme.VetAppTheme
import androidx.navigation.compose.composable
import androidx.room.Room
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.ui.LoginScreen
import com.example.vetapp.ui.CreateAccountScreen
import com.example.vetapp.ui.DashboardScreen
import com.example.vetapp.ui.ReportTemplateScreen

class MainActivity : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //init database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "VetApp"
        ).build()

        enableEdgeToEdge()
        setContent {
            VetAppTheme {
                VetApp()
            }
        }
    }
}

@Composable
fun VetApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ReportsTemplate.route) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.CreateAccount.route) { CreateAccountScreen(navController) }
        composable(Screen.Dashboard.route) { DashboardScreen() }
        composable(Screen.ReportsTemplate.route) { ReportTemplateScreen() }
    }
}