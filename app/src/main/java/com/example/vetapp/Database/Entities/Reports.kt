package com.example.vetapp.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reports (
    @PrimaryKey val Id : Int = -1,
     val Name : String,
)

