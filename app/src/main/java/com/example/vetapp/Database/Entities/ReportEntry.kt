package com.example.vetapp.Database.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(foreignKeys = [ForeignKey(
    entity = Reports::class,
    parentColumns = arrayOf("Id"),
    childColumns = arrayOf("reportId"),
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
),
ForeignKey(
    entity = ReportTemplateField::class,
    parentColumns = arrayOf("uid"),
    childColumns = arrayOf("templateId"),
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)],
    indices = [
        Index("reportId"),
        Index("templateId")
    ])
data class ReportEntry (
    @PrimaryKey(autoGenerate = true) val Id : Int = 0,
    var value : String,
    var reportId: Int,
    var templateId : Int,
    var timestamp : String,
    var sent : Boolean = false
)