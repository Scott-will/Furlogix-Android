package com.example.vetapp.repositories

import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.Database.Entities.Reports
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportsRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val reportDao : ReportsDao) : IReportsRepository  {

    override fun reportsObservable(): Flow<List<Reports>> {
        return reportDao.getAllFlow()
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

    override suspend  fun insertReport(report: Reports) {
        return reportDao.insert(report)
    }

    override suspend  fun updateReport(report: Reports) {
        return reportDao.update(report)
    }

    override suspend  fun deleteReport(report: Reports) {
            return reportDao.delete(report)
        }
}