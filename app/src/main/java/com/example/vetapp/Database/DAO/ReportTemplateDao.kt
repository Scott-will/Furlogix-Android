package com.example.vetapp.Database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vetapp.Database.Entities.ReportTemplateField
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

    @Query("SELECT rtf.* FROM REPORTTEMPLATEFIELD rtf " +
            "JOIN Reports r on  rtf.reportId = r.Id " +
            "JOIN user_table u on r.userId = u.uid" +
            " WHERE u.uid = :userId AND rtf.favourite = 1")
    fun getFavouriteReportTemplateForUser(userId : Long): List<ReportTemplateField>

    @Query("Update reporttemplatefield Set favourite = Not favourite Where uid = :id")
    fun flipFavouriteReportTemplateFieldById(id : Int)

}