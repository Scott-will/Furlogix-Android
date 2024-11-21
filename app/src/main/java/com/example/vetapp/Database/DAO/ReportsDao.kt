package com.example.vetapp.Database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportsDao {
    @Query("SELECT * FROM Reports")
    fun getAll(): Flow<List<Reports>>

    @Insert
    fun insert(vararg reports: Reports)

    @Update
    fun update(vararg report : Reports)

    @Delete
    fun delete(vararg report : Reports)
}