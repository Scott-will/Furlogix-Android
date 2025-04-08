package com.furlogix.repositories

import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Result
import kotlinx.coroutines.flow.Flow

interface IReportEntryRepository {

    suspend fun insertEntries(entries : List<ReportEntry> ) : Result

    fun getAllEntriesForReport(reportId : Int) : Flow<ReportEntry>

    suspend fun getAllReportEntriesById(reportId : Int) : List<ReportEntry>

    suspend fun updateReportEntries(entries : List<ReportEntry>)

    suspend fun deleteSentReportEntries()

    suspend fun getSizeOfReportEntryTableKB() : Int

    suspend fun getAllReportEntries(reportId : Int) : List<ReportEntry>

    suspend fun getAllEntriesForReportTemplate(reportTemplateId : Int) : List<ReportEntry>

    suspend fun getAllReportEntriesForTemplate(reportTemplateId : Int): Flow<List<ReportEntry>>
}