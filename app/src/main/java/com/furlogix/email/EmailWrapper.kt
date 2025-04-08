package com.furlogix.email

import android.net.Uri

class EmailWrapper(
    val ToEmailAddress: String,
    val Subject: String,
    val BodyText: String,
    val fileUri : Uri
)