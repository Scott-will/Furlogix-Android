package com.example.vetapp.email

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
class EmailHandler {
    val TAG: String = "EmailService"
    fun SendEmail(email: EmailWrapper?) {
    }
    fun SendEmail(context: Context, emailIntent: Intent) {
        try{
            context.startActivity(emailIntent)
        }
        catch (e : ActivityNotFoundException){
            Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
    fun CreateEmail(context: Context, email: EmailWrapper) : Intent {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps handle this.
            putExtra(Intent.EXTRA_EMAIL, arrayOf("placeholder@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "test")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        this.SendEmail(context, emailIntent)
        return emailIntent
    }
    fun CreateAndSendEmail(context: Context, email: EmailWrapper){
        val emailIntent = this.CreateEmail(context, email)
        this.SendEmail(context, emailIntent)
    }
}