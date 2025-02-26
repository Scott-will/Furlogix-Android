package com.example.vetapp.Database.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.vetapp.reports.FieldType


@Entity(foreignKeys = [ForeignKey(
    entity = Reports::class,
    parentColumns = arrayOf("Id"),
    childColumns = arrayOf("reportId"),
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)],
    indices = [
        Index("reportId")
    ]
)
data class ReportTemplateField(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    var reportId: Int,
    var fieldType : FieldType,
    var name : String,
    var favourite : Boolean = false,
    var optional : Boolean = true

)