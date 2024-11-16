package com.example.vetapp.Database.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.vetapp.reports.FieldType


@Entity(foreignKeys = [ForeignKey(
    entity = Reports::class,
    parentColumns = arrayOf("Id"),
    childColumns = arrayOf("reportId"),
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)])
data class ReportTemplateField(
    @PrimaryKey val uid: Int,
    val reportId: Int,
    val fieldType : FieldType,
    val name : String
)