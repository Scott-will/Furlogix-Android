package com.example.vetapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vetapp.Database.DAO.RemindersDao
import com.example.vetapp.Database.DAO.ReportEntryDao
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.Reminder
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.Database.Entities.User

@Database(entities = [User::class,
    ReportTemplateField::class,
    Reports::class,
    ReportEntry::class,
    Reminder::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun reportTemplateDao() : ReportTemplateDao

    abstract fun reportsDao() : ReportsDao

    abstract fun reportEntryDao() : ReportEntryDao

    abstract fun remindersDao() : RemindersDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vetapp_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}