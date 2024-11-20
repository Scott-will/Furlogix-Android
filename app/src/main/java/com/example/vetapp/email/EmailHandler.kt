package com.example.vetapp.email

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import javax.inject.Inject

class EmailHandler @Inject constructor(
    private val context: Context
) : IEmailHandler {
    val TAG: String = "EmailService"
    override fun SendEmail(email: EmailWrapper?) {
    }

    fun SendEmail(emailIntent: Intent) {
        try{
            context.startActivity(emailIntent)
        }
        catch (e : ActivityNotFoundException){
            Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun CreateEmail(wrapper: EmailWrapper?) : Intent {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps handle this.
            putExtra(Intent.EXTRA_EMAIL, arrayOf("placeholder@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "test")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        this.SendEmail(emailIntent)
        return emailIntent
    }
    override  fun CreateAndSendEmail(email: EmailWrapper){
        val emailIntent = this.CreateEmail(email)
        this.SendEmail(emailIntent)
    }
}