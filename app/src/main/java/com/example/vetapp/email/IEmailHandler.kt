package com.example.vetapp.email
import android.content.Context
import android.content.Intent
interface IEmailHandler {
    fun CreateAndSendEmail(context: Context, email: EmailWrapper)
    fun CreateEmail(context: Context?, wrapper: EmailWrapper?) : Intent
    fun SendEmail(email: EmailWrapper?)
    fun SendEmail()
}