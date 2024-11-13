package com.example.vetapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.Entities.User
import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.ReportTemplateField

@Database(entities = [User::class, ReportTemplateField::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun reportTemplateDao() : ReportTemplateDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}