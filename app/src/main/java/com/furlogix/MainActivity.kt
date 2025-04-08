package com.furlogix

import ReportCleanerWorker
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.furlogix.R
import com.furlogix.Database.AppDatabase
import com.furlogix.reminders.RequestCodeFactory
import com.furlogix.ui.navigation.ComposeNavGraph
import com.furlogix.ui.theme.VetAppTheme
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

