package com.example.vetapp.email

public class EmailWrapper(toEmailAddress: String, fromEmailAddress: String, subject: String, bodyText: String) {
    var FromEmailAddress: String = fromEmailAddress
    var ToEmailAddress: String = toEmailAddress
    var Subject: String = subject
    var BodyText: String = bodyText
}