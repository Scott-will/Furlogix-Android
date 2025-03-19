package com.example.vetapp

import ReportCleanerWorker
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.reminders.RequestCodeFactory
import com.example.vetapp.ui.AppHeader
import com.example.vetapp.ui.navigation.ComposeNavGraph
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
import com.example.vetapp.ui.theme.VetAppTheme
import com.example.vetapp.viewmodels.PetViewModel
import com.example.vetapp.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        //setup alarms
        this.setUpAlarmData()
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=  PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }
        enableEdgeToEdge()
        setContent {
            VetAppTheme {
                ComposeNavGraph()
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

    fun setUpAlarmData(){
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                RequestCodeFactory.InitRequestCode()
            }

        }
    }
}

