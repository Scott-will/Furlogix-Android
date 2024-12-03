package com.example.vetapp.Database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vetapp.Database.Entities.ReportEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportEntryDao {
    @Insert
    fun insert(vararg entry: ReportEntry)

    @Query("SELECT * FROM REPORTENTRY Where reportId = :reportId")
    fun getAllEntriesForReport(reportId : Int) : Flow<ReportEntry>

    @Query("SELECT * FROM REPORTENTRY where reportId = :reportId")
    fun getAllReportEntries(reportId: Int) : List<ReportEntry>
}