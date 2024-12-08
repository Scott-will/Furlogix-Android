package com.example.vetapp.repositories

import android.widget.Toast
import com.example.vetapp.Database.DAO.ReportEntryDao
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.VetApplication
import com.example.vetapp.reports.ReportEntryValidator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportEntryRepository @Inject constructor(
    private val reportEntryDao : ReportEntryDao,
    private val reportEntryValidator: ReportEntryValidator
) : IReportEntryRepository {

    override suspend fun insertEntries(entries : List<ReportEntry> ) : Boolean{
        entries.forEach { entry ->
            if(!reportEntryValidator.ValidateEntry(entry)){
                return false
            }
        }
        entries.forEach { entry ->
            reportEntryDao.insert(entry)
        }
        Toast.makeText(VetApplication.applicationContext(), "Entries saved successfully", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun getAllEntriesForReport(reportId : Int) : Flow<ReportEntry>{
        return reportEntryDao.getAllEntriesForReport(reportId)
    }

    override suspend fun getAllReportEntries(reportId : Int) : List<ReportEntry>{
        return reportEntryDao.getAllReportEntries(reportId)
    }
}