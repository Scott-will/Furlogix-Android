package com.example.vetapp.repositories

import android.database.Observable
import androidx.lifecycle.LiveData
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.VetApplication
import com.example.vetapp.injectionModules.ReportsRepositoryModule
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportTemplateRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val reportTemplateDao :ReportTemplateDao) : IReportTemplateRepository {
        //private val reportTemplateDao : ReportTemplateDao = AppDatabase.getDatabase(VetApplication.applicationContext()).reportTemplateDao()

        override fun ReportTemplateObservable() : LiveData<List<ReportTemplateField>> {
            return reportTemplateDao.getAll()
        }

        override fun insertReportTemplateFields(reportTemplateFields : List<ReportTemplateField>){
            return reportTemplateDao.insertAll()
        }

        override fun GetReportById(id: Int): LiveData<List<ReportTemplateField>> {
            return reportTemplateDao.getReportById(id)
        }

}