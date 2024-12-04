package com.example.vetapp.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val name: String,
    val surname: String,
    val email: String,
)