package com.furlogix.email

class EmailValidator {
    companion object{
        fun ValidateEmail(email : String) : Boolean{
                return !email.isBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        }
    }
