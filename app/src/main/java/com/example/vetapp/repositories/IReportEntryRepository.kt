package com.example.vetapp.repositories

import com.example.vetapp.Database.Entities.ReportEntry
import kotlinx.coroutines.flow.Flow

interface IReportEntryRepository {

    fun insertEntries(entries : List<ReportEntry> )

    fun getAllEntriesForReport(reportId : Int) : Flow<ReportEntry>

    suspend fun getAllReportEntries(reportId : Int) : List<ReportEntry>
}