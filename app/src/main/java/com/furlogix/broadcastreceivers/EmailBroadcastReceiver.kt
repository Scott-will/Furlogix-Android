package com.furlogix.broadcastreceivers

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.furlogix.logger.ILogger

class EmailBroadcastReceiver(
    private val logger : ILogger) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            ContextCompat.startActivity(context, intent, null)
        } catch (ex: ActivityNotFoundException) {
            logger.logError(TAG, String.format("No activity found : %s", ex), ex)
        }
    }
    companion object {
        private const val TAG = "com.example.vetapp.broadcastreceivers.EmailBroadcastReceiver"
    }
}