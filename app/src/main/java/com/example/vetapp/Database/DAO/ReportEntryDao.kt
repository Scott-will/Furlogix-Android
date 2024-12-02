package com.example.vetapp.Database.DAO

import androidx.room.Dao
import androidx.room.Insert
import com.example.vetapp.Database.Entities.ReportEntry

@Dao
interface ReportEntryDao {
    @Insert
    fun insert(vararg entry: ReportEntry)
}