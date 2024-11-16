package com.example.vetapp.Database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports

@Dao
interface ReportsDao {
    @Query("SELECT * FROM Reports")
    fun getAll(): LiveData<List<Reports>>

    @Insert
    fun insert(vararg reports: Reports)
}