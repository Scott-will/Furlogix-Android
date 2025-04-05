package com.furlogix.Database.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
    entity = Pet::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("petId"),
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)])
data class Reports (
    @PrimaryKey (autoGenerate = true) val Id : Int = 0,
    var name : String,
    var petId : Int
)

