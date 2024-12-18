package com.example.vetapp.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder (
    @PrimaryKey(autoGenerate = true) val Id : Int = 0,
    var frequency : String,
    var type : String,
    var startTime : String
)