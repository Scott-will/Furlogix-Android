package com.furlogix.Database.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "pet_table",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["uid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Pet(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    var name: String,
    var type: String,
    val description: String,
    val userId: Long,
    var photoUri: String? = null
)