package com.example.vetapp.reminders;

import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.RemindersDao
import com.example.vetapp.VetApp
import com.example.vetapp.VetApplication
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.last

public class RequestCodeFactory {
    public companion object {
        private var latestRequestCode = 0;

        fun GetRequestCode() : Int{
            latestRequestCode += 1
            return latestRequestCode
        }

        fun InitRequestCode(){
            val context = VetApplication.applicationContext()
            val db = AppDatabase.getDatabase(context)
            var reminder = db.remindersDao().getLargestRequestCode();
            if(reminder == null){
                latestRequestCode = 1
                return
            }
            latestRequestCode = reminder.requestCode;
        }
    }
}
