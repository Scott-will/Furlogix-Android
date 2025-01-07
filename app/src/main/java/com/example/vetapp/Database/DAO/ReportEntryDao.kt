package com.example.vetapp.Database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vetapp.Database.Entities.ReportEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportEntryDao {
    @Insert
    fun insert(vararg entry: ReportEntry)

    @Query("SELECT * FROM REPORTENTRY Where reportId = :reportId")
    fun getAllEntriesForReport(reportId : Int) : Flow<ReportEntry>

    @Query("SELECT * FROM REPORTENTRY where reportId = :reportId")
    fun getAllReportEntriesById(reportId: Int) : List<ReportEntry>

    @Query("DELETE From ReportEntry WHERE sent = 1")
    fun deleteSentReportEntries()

    @Update
    fun updateReportEntries(vararg entries : ReportEntry)

    @Query("Select * From ReportEntry")
    fun getAllReportEntries() : List<ReportEntry>

    @Query("SELECT * FROM REPORTENTRY WHERE templateId = :reportTemplateId")
    fun getAllReportEntriesForTemplate(reportTemplateId : Int) : Flow<List<ReportEntry>>
}