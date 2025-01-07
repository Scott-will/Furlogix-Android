package com.example.vetapp.repositories

import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Result
import kotlinx.coroutines.flow.Flow

interface IReportEntryRepository {

    suspend fun insertEntries(entries : List<ReportEntry> ) : Result

    fun getAllEntriesForReport(reportId : Int) : Flow<ReportEntry>

    suspend fun getAllReportEntries(reportId : Int) : List<ReportEntry>

    suspend fun getAllEntriesForReportTemplate(reportTemplateId : Int) : List<ReportEntry>
}