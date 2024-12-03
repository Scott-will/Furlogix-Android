package com.example.vetapp.email
import android.content.Intent
interface IEmailHandler {
    fun CreateAndSendEmail(email: EmailWrapper)
    fun CreateEmail(wrapper: EmailWrapper) : Intent
    fun SendEmail(email: EmailWrapper?)
}