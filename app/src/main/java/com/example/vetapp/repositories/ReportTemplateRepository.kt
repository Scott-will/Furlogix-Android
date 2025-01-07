package com.example.vetapp.repositories

import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Result
import com.example.vetapp.reports.ReportTemplateValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportTemplateRepository @Inject constructor(
    private val reportTemplateValidator: ReportTemplateValidator,
    private val reportTemplateDao :ReportTemplateDao) : IReportTemplateRepository {

    override fun ReportTemplateObservable() : Flow<List<ReportTemplateField>> {
        return reportTemplateDao.getAllFlow()
    }

    override suspend fun GetAllReportTemplates(): List<ReportTemplateField> {
        return reportTemplateDao.getAll()
    }

    override suspend fun insertReportTemplateField(reportTemplateField : ReportTemplateField) : Result{
        var result = reportTemplateValidator.ValidateTemplate(reportTemplateField)
        if(!result.result){
            return result
        }
        reportTemplateDao.insert(reportTemplateField)
        return Result(true, "")
    }

    override suspend fun GetReportByIdFlow(id: Int): Flow<List<ReportTemplateField>> {
        return reportTemplateDao.getReportByIdFlow(id)
    }

    override suspend fun GetReportById(id: Int): List<ReportTemplateField> {
        return reportTemplateDao.getReportById(id)
    }

    override suspend fun updateReportTemplateField(reportTemplateField : ReportTemplateField) : Result{
        var result = reportTemplateValidator.ValidateTemplate(reportTemplateField)
        if(!result.result){
            return result
        }
        reportTemplateDao.update(reportTemplateField)
        return Result(true, "")
    }
    override suspend fun deleteReportTemplateField(reportTemplateField : ReportTemplateField){
        return reportTemplateDao.delete(reportTemplateField)
    }

    override suspend fun GetTemplateById(id : Int) : ReportTemplateField{
        return reportTemplateDao.getTemplateById(id)
    }

    override suspend fun GetFavouriteReportTemplatesForUser(userId : Int) : List<ReportTemplateField>{
        return reportTemplateDao.getFavouriteReportTemplateForUser(userId)
    }

}