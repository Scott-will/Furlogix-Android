package com.furlogix.email

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.furlogix.Database.DAO.UserDao
import com.furlogix.repositories.IUserRepository
import com.furlogix.repositories.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmailHandler @Inject constructor(
    private val context: Context,
    private val userRepository: IUserRepository
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

    override fun CreateEmail(wrapper: EmailWrapper) : Intent {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(wrapper.ToEmailAddress))
            putExtra(Intent.EXTRA_SUBJECT, wrapper.Subject)
            putExtra(Intent.EXTRA_TEXT, wrapper.BodyText)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Intent.EXTRA_STREAM, wrapper.fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.grantUriPermission(
            "com.google.android.gm", wrapper.fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        this.SendEmail(emailIntent)
        return emailIntent
    }

    override  fun CreateAndSendEmail(email: EmailWrapper){
        val emailIntent = this.CreateEmail(email)
        GlobalScope.launch {
            val userId = userRepository.getCurrentUserId()
            userRepository.setPendingReportsForUser(userId)
        }

        this.SendEmail(emailIntent)
    }
}