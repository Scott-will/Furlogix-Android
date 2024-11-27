package com.example.vetapp.repositories

import com.example.vetapp.Database.Entities.ReportTemplateField
import kotlinx.coroutines.flow.Flow

interface IReportTemplateRepository {
    fun ReportTemplateObservable() : Flow<List<ReportTemplateField>>

    suspend fun insertReportTemplateField(reportTemplateField : ReportTemplateField)
    
    suspend fun GetReportById(id : Int) : Flow<List<ReportTemplateField>>

    suspend fun deleteReportTemplateField(reportTemplateField : ReportTemplateField)

    suspend fun updateReportTemplateField(reportTemplateField : ReportTemplateField)

}