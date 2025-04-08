package com.furlogix.repositories

import com.furlogix.Database.DAO.ReportEntryDao
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Result
import com.furlogix.reports.ReportEntryValidator
import kotlinx.coroutines.flow.Flow
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import javax.inject.Inject


class ReportEntryRepository @Inject constructor(
    private val reportEntryDao : ReportEntryDao,
    private val reportEntryValidator: ReportEntryValidator
) : IReportEntryRepository {

    override suspend fun insertEntries(entries : List<ReportEntry> ) : com.furlogix.Result{
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

    override suspend fun getAllEntriesForReportTemplate(reportTemplateId : Int) : List<ReportEntry>{
        return reportEntryDao.getAllReportEntriesForTemplate(reportTemplateId)
    }

    override suspend fun getAllReportEntries(reportId : Int) : List<ReportEntry>{
        return reportEntryDao.getAllReportEntries()
    }

    override suspend fun getAllReportEntriesById(reportId : Int) : List<ReportEntry>{
        return reportEntryDao.getAllReportEntriesById(reportId)
    }

    override suspend fun updateReportEntries(entries : List<ReportEntry>){
        return reportEntryDao.updateReportEntries(*entries.toTypedArray())
    }

    override suspend fun deleteSentReportEntries(){
        return reportEntryDao.deleteSentReportEntries()
    }

    override suspend fun getSizeOfReportEntryTableKB(): Int {
        var entries = reportEntryDao.getAllReportEntries()
        val byteArrayOutputStream = ByteArrayOutputStream()
        ObjectOutputStream(byteArrayOutputStream).use { objectOutputStream ->
            objectOutputStream.writeObject(entries)
        }
        return byteArrayOutputStream.size()/1024
    }

    override suspend fun getAllReportEntriesForTemplate(reportTemplateId : Int) : Flow<List<ReportEntry>>{
        return reportEntryDao.getAllReportEntriesForTemplateAsFlow(reportTemplateId)
    }
}