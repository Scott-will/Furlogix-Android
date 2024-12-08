package com.example.vetapp.reports

import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import javax.inject.Inject

class ReportTemplateValidator @Inject constructor(
){

    suspend fun ValidateTemplate(template : ReportTemplateField) : Boolean{
        if(template.name.isNullOrEmpty()){
            return false
        }
        return true
    }
}