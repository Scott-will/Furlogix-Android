package com.example.vetapp.repositories

import com.example.vetapp.Database.DAO.ReportEntryDao
import com.example.vetapp.Database.Entities.ReportEntry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ReportEntryRepository @Inject constructor(
    private val reportEntryDao : ReportEntryDao
) : IReportEntryRepository {

    override fun insertEntries(entries : List<ReportEntry> ){
        entries.forEach { entry ->
            reportEntryDao.insert(entry)
        }
    }
}