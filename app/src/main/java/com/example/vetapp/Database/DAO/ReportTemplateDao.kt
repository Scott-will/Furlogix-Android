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
interface ReportTemplateDao {
    @Query("SELECT * FROM ReportTemplateField")
    fun getAll(): Flow<List<ReportTemplateField>>

    @Insert
    fun insert(vararg users: ReportTemplateField)

    @Query("SELECT * FROM REPORTTEMPLATEFIELD WHERE reportId = :id")
    fun getReportById(id: Int): Flow<List<ReportTemplateField>>

    @Update
    fun update(vararg report : ReportTemplateField)

    @Delete
    fun delete(vararg report : ReportTemplateField)
}