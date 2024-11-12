package com.example.vetapp

import android.app.Application
import android.content.Context



class VetApplication : Application() {
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
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = VetApplication.applicationContext()
    }

}