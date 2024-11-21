package com.example.vetapp.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.Database.Entities.User
import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports

@Database(entities = [User::class, ReportTemplateField::class, Reports::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun reportTemplateDao() : ReportTemplateDao

    abstract fun reportsDao() : ReportsDao
}