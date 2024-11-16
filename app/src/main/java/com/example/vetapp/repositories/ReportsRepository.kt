package com.example.vetapp.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.DAO.ReportsDao
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.VetApplication
import com.example.vetapp.injectionModules.ReportsRepositoryModule
import dagger.Component
import dagger.hilt.android.qualifiers.ApplicationContext
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

        override fun insertReport(report: Reports) {
            return reportDao.insert()
        }
}