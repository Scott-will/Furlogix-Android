package com.example.vetapp.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vetapp.reports.FieldType


@Entity
data class ReportTemplateField(
    @PrimaryKey val uid: Int,
    val reportId: Int,
    val fieldType : FieldType,
    val name : String
)