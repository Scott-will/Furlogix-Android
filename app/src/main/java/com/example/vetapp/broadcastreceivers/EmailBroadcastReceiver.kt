package com.example.vetapp.broadcastreceivers

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

class EmailBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            ContextCompat.startActivity(context, intent, null)
        } catch (ex: ActivityNotFoundException) {
            Log.e(TAG, String.format("No activity found : %s", ex))
        }
    }
    companion object {
        private const val TAG = "com.example.vetapp.broadcastreceivers.EmailBroadcastReceiver"
    }
}