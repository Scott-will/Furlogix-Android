package com.example.vetapp.repositories

import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.Result
import com.example.vetapp.reports.ReportValidator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportsRepository @Inject constructor(
    private val reportValidator: ReportValidator,
    private val reportDao : ReportsDao) : IReportsRepository  {

    override fun reportsForPetObservable(petId : Int): List<Reports> {
        return reportDao.getAllForPetFlow(petId)
    }

    override suspend fun getAllReports(): List<Reports> {
        return reportDao.getAllReports()
    }

    override fun getReportByIdFlow(id : Int): Flow<Reports>{
        return reportDao.getByReportIdFlow(id)
    }

    override suspend fun getReportById(id: Int) : Reports{
        return reportDao.getByReportId(id)
    }

    override suspend  fun insertReport(report: Reports) : Result{
        var result = reportValidator.ValidateReport(report)
        if(!result.result){
            return result
        }
        reportDao.insert(report)
        return Result(true, "Report added successfully")
    }

    override suspend  fun updateReport(report: Reports) : Result{
        var result = reportValidator.ValidateReport(report)
        if(!result.result){
            return result
        }
        reportDao.update(report)
        return Result(true, "Report added successfully")
    }

    override suspend  fun deleteReport(report: Reports) {
            return reportDao.delete(report)
        }
}