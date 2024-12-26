package com.example.vetapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import com.example.vetapp.di.CustomHiltWorkerFactory

import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class VetApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CustomHiltWorkerFactory
    init {
        instance = this
    }

    companion object {
        private var instance: VetApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = VetApplication.applicationContext()
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
}

