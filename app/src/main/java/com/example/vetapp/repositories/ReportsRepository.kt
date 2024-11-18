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
            return reportDao.getAll()
        }

        override suspend  fun insertReport(report: Reports) {
            return reportDao.insert(report)
        }
}