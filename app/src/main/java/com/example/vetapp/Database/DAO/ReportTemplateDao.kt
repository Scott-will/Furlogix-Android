package com.example.vetapp.Database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vetapp.Database.Entities.ReportTemplateField


@Dao
interface ReportTemplateDao {
    @Query("SELECT * FROM ReportTemplateField")
    fun getAll(): LiveData<List<ReportTemplateField>>

    @Insert
    fun insertAll(vararg users: ReportTemplateField)

    @Query("SELECT * FROM REPORTTEMPLATEFIELD WHERE reportId = :id")
    fun getReportById(id: Int): LiveData<List<ReportTemplateField>>
}