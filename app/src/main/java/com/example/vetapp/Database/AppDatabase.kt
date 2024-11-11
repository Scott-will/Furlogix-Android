package com.example.vetapp.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vetapp.Database.Entities.User
import com.example.vetapp.Database.DAO.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}