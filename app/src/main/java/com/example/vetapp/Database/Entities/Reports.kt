package com.example.vetapp.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reports (
    @PrimaryKey (autoGenerate = true) val Id : Int = 0,
     var name : String,
)

