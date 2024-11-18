package com.example.vetapp.repositories

import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.Entities.ReportTemplateField
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportTemplateRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val reportTemplateDao :ReportTemplateDao) : IReportTemplateRepository {

        override fun ReportTemplateObservable() : Flow<List<ReportTemplateField>> {
            return reportTemplateDao.getAll()
        }

        override suspend fun insertReportTemplateFields(reportTemplateFields : List<ReportTemplateField>){
            return reportTemplateDao.insertAll()
        }

        override suspend fun GetReportById(id: Int): Flow<List<ReportTemplateField>> {
            return reportTemplateDao.getReportById(id)
        }

}