package com.furlogix.reminders;

import com.furlogix.Database.AppDatabase
import com.furlogix.Furlogix

public class RequestCodeFactory {
    public companion object {
        private var latestRequestCode = 0;

        fun GetRequestCode() : Int{
            if (latestRequestCode >= Int.MAX_VALUE) {
                latestRequestCode = 0
            }
            latestRequestCode += 1
            return latestRequestCode
        }

        fun InitRequestCode(){
            val context = Furlogix.applicationContext()
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
