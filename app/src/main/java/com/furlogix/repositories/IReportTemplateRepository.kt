package com.furlogix.repositories

import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.Result
import kotlinx.coroutines.flow.Flow

interface IReportTemplateRepository {
    fun ReportTemplateObservable() : Flow<List<ReportTemplateField>>

    suspend fun GetAllReportTemplates() : List<ReportTemplateField>

    suspend fun insertReportTemplateField(reportTemplateField : ReportTemplateField) : Result
    
    suspend fun GetReportByIdFlow(id : Int) : Flow<List<ReportTemplateField>>

    suspend fun GetFavouriteReportTemplatesForPet(petId : Int) : List<ReportTemplateField>

    suspend fun GetTemplateById(id : Int) : ReportTemplateField

    suspend fun GetReportById(id : Int) : List<ReportTemplateField>

    suspend fun deleteReportTemplateField(reportTemplateField : ReportTemplateField)

    suspend fun updateReportTemplateField(reportTemplateField : ReportTemplateField) : Result

    suspend fun flipFavouriteReportTemplateField(reportTemplateFieldId : Int)

}