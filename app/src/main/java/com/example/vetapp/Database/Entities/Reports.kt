package com.example.vetapp.Database.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
    entity = User::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("userId"),
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)])
data class Reports (
    @PrimaryKey (autoGenerate = true) val Id : Int = 0,
    var name : String,
    var userId : Long
)

