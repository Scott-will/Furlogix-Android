package com.furlogix.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furlogix.Database.DAO.PetDao
import com.furlogix.Database.DAO.RemindersDao
import com.furlogix.Database.DAO.ReportEntryDao
import com.furlogix.Database.DAO.ReportTemplateDao
import com.furlogix.Database.DAO.ReportsDao
import com.furlogix.Database.DAO.UserDao
import com.furlogix.Database.Entities.Pet
import com.furlogix.Database.Entities.Reminder
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.Database.Entities.Reports
import com.furlogix.Database.Entities.User

@Database(entities = [User::class,
    ReportTemplateField::class,
    Reports::class,
    ReportEntry::class,
    Reminder::class,
    Pet::class], version = 13)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun petDao(): PetDao

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
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}