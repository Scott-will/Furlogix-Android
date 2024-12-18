package com.example.vetapp.repositories

import android.widget.Toast
import com.example.vetapp.Database.DAO.ReportEntryDao
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Result
import com.example.vetapp.VetApplication
import com.example.vetapp.reports.ReportEntryValidator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportEntryRepository @Inject constructor(
    private val reportEntryDao : ReportEntryDao,
    private val reportEntryValidator: ReportEntryValidator
) : IReportEntryRepository {

    override suspend fun insertEntries(entries : List<ReportEntry> ) : com.example.vetapp.Result{
        entries.forEach { entry ->
            var result = reportEntryValidator.ValidateEntry(entry)
            if(!result.result){
                return result
            }
        }
        entries.forEach { entry ->
            reportEntryDao.insert(entry)
        }
        return Result(true, "Entries added successfully")
    }

    override fun getAllEntriesForReport(reportId : Int) : Flow<ReportEntry>{
        return reportEntryDao.getAllEntriesForReport(reportId)
    }

    override suspend fun getAllReportEntries(reportId : Int) : List<ReportEntry>{
        return reportEntryDao.getAllReportEntries(reportId)
    }
}