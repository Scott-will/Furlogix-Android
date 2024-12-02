package com.example.vetapp.repositories

import com.example.vetapp.Database.DAO.ReportEntryDao
import com.example.vetapp.Database.Entities.ReportEntry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportEntryRepository @Inject constructor(
    private val reportEntryDao : ReportEntryDao
) : IReportEntryRepository {

    override fun insertEntries(entries : List<ReportEntry> ){
        entries.forEach { entry ->
            reportEntryDao.insert(entry)
        }
    }

    override fun getAllEntriesForReport(reportId : Int) : Flow<ReportEntry>{
        return reportEntryDao.getAllEntriesForReport(reportId)
    }

    override suspend fun getAllReportEntries(reportId : Int) : List<ReportEntry>{
        return reportEntryDao.getAllReportEntries(reportId)
    }
}