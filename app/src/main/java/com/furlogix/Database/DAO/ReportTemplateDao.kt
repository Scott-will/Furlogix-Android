package com.furlogix.Database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.furlogix.Database.Entities.ReportTemplateField
import kotlinx.coroutines.flow.Flow


@Dao
interface ReportTemplateDao {
    @Query("SELECT * FROM ReportTemplateField")
    fun getAllFlow(): Flow<List<ReportTemplateField>>

    @Query("SELECT * FROM ReportTemplateField")
    fun getAll(): List<ReportTemplateField>

    @Insert
    fun insert(vararg users: ReportTemplateField)

    @Query("SELECT * FROM REPORTTEMPLATEFIELD WHERE reportId = :id")
    fun getReportByIdFlow(id: Int): Flow<List<ReportTemplateField>>

    @Query("SELECT * FROM REPORTTEMPLATEFIELD WHERE reportId = :id")
    fun getReportById(id: Int): List<ReportTemplateField>

    @Query("SELECT * FROM REPORTTEMPLATEFIELD WHERE uid = :id")
    fun getTemplateById(id : Int) : ReportTemplateField

    @Update
    fun update(vararg report : ReportTemplateField)

    @Delete
    fun delete(vararg report : ReportTemplateField)
}